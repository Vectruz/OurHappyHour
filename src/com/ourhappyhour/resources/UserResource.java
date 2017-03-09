package com.ourhappyhour.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ourhappyhour.entitys.User;
import com.ourhappyhour.services.UserService;
import com.ourhappyhour.utils.Json;

@Path("/user")
public class UserResource {
	
	UserService userService = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		User user = userService.getUserById(id);
		if (user.getId() == null) {
			return Response.noContent().build();
		}
		return Response.ok(Json.toJson(user)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		ArrayList<User> userList = userService.getUsers();
		return Response.ok(Json.toJson(userList)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(String json) {
		boolean success = userService.postUser(json);
		if (!success) {
			return Response.status(400).build();
		}
		return Response.status(201).build();
	}
	
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postById(String json) {
		boolean success = userService.putUser(json);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(String json) {
		boolean success = userService.putUser(json);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") int id) {
		boolean success = userService.deleteById(id);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
		
	}

}
