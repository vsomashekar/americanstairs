package com.american.stairs.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.american.stairs.model.User;
import com.american.stairs.service.UserService;

@Path("/users")  // This line maps the URI /messages to this servlet 
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)  // Put the consumes and produces at the beginning of the class if all method uses JSON
public class UserResource {
	UserService usrSrv = new UserService();
	
	//http://localhost:8080/american-stairs-API/webapi/users/
	//http://localhost:8080/american-stairs-API/webapi/users/?year=2017
	//http://localhost:8080/american-stairs-API/webapi/users/?start=0&size=1
	@GET
	public List<User> getUsers(){		
/*		if(year > 0)
		{
			System.out.println("Year given");
			return msgSrv.getAllMessagesByYear(year);
		}
		if(start >= 0 && size > 0)
		{
			System.out.println("Pagination " + start + size);
			return msgSrv.getAllMessagesPagination(start, size);
		}
		else*/
		{
			System.out.println("Plain get");
			return usrSrv.getAllUsers();	
		}
	}
	
	@GET
	@Path("/{userId}")
	//http://localhost:8080/messenger/webapi/messages/1 -- Gets message for ID 1
	public User getUser(@PathParam("userId") Integer userId, @Context UriInfo uriInfo ){
		System.out.println("getuser by userid method");
		User user = usrSrv.getUser(userId);
		
		user.addLink(getUriForSelf(user, uriInfo), "self");
		return user;
	}
	
	private String getUriForSelf(User user, UriInfo uriInfo) {
		String uriBuilder = uriInfo.getBaseUriBuilder()       //http://localhost:8080/messenger-RESTAPI/webapi/
		        .path(UserResource.class)			// /messages
		        .path(Long.toString(user.getUserId()))				// /id
		        .build().toString();
		return uriBuilder;
	}

	@POST
	public Response addUser(User user, @Context UriInfo uriInfo) {
		User newUser = usrSrv.addUser(user);
			//return Rere.created(new URI("messenger/webapi/messages" + newMessage.getId())) //location of the newly created URIto 
			// Instead of hard coding it like above use the context to get the current URI Path
				String newId = String.valueOf(newUser.getUserId());
				URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
				return Response.created(uri)
					.entity(newUser)
					.build();

		/*return Response.status(Status.CREATED)
				.entity(newMessage)
				.build();*/
		//return msgSrv.addMessage(message);
	}
	
	@PUT
	@Path("/{userId}")
	//http://localhost:8080/american-stairs-API/webapi/users/1  -- Updates user with ID 1
	public User update(@PathParam("userId") Integer userId, User user){
		System.out.println("Update the user");
		user.setUserId(userId);
		return usrSrv.updateUser(user);
	}
	
	@DELETE
	@Path("/{userId}")
	//http://localhost:8080/american-stairs-API/webapi/users/1   -- Deletes message with ID 1
	public void delete(@PathParam("userId") Integer userId){
		usrSrv.deleteUser(userId);
	}
}
