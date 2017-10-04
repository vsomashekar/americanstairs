package com.american.stairs.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.american.stairs.model.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable arg0) {
		
		ErrorMessage errorMessage = new ErrorMessage(arg0.getMessage(), 500, "www.american-stairs-Web-API.com") ;
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				   .entity(errorMessage)
				   .build();
	}

}
