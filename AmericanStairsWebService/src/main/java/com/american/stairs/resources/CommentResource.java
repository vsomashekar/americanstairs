package com.american.stairs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class CommentResource {

	@GET
	//http://localhost:8080/messenger/webapi/messages/12/comments
	public String Test(){
		return "Test";
	}
	
	@GET
	@Path("{commentId}")
	//http://localhost:8080/messenger/webapi/messages/12/comments/1
	public String Test2(){
		return "Test2";
	}
}
