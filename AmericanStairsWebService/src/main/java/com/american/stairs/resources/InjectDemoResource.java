package com.american.stairs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/injectdemo")  // This line maps the URI /messages to this servlet 
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)  
public class InjectDemoResource {
	
	@GET
	@Path("annotations")
	// http://localhost:8080/messenger/webapi/injectdemo/annotations
	//http://localhost:8080/messenger/webapi/injectdemo/annotations:param=value
	// Add specific value in the header and cookie in Postman in google apps
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,  // Not working for some reason
			                                @HeaderParam("CustomHeaderParam") String HeaderParam, // useful to access the authentication tokens
			                                @CookieParam("name") String cookieParam)  // Need to create a test cookie to test this out
	{
		return "Matrix Param is " + matrixParam + " Header Param is " + HeaderParam + " Cookie param " + cookieParam;
	}
	
	@GET
	@Path("context")
	//injectdemo/annotations/context
	public String getParamsUsingContext(@Context UriInfo urlinfo, @Context HttpHeaders headers )
	{
		String urlpath = urlinfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "urlpath is  " + urlpath + " cookies is " +  cookies;
	}

}
