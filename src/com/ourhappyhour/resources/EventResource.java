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

import com.ourhappyhour.entitys.Event;
import com.ourhappyhour.services.EventService;
import com.ourhappyhour.utils.Json;

@Path("/event")
public class EventResource {
	
	EventService eventService = new EventService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		Event event = eventService.getById(id);
		if (event.getId() == null) {
			return Response.noContent().build();
		}
		return Response.ok(Json.toJson(event)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		ArrayList<Event> eventList = eventService.getAll();
		return Response.ok(Json.toJson(eventList)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(String json) {
		boolean success = eventService.postEvent(json);
		if (!success) {
			return Response.status(400).build();
		}
		return Response.status(201).build();
	}
	
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postById(String json) {
		boolean success = eventService.putEvent(json);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(String json) {
		boolean success = eventService.putEvent(json);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") int id) {
		boolean success = eventService.deleteById(id);
		if(!success) {
			return Response.status(400).build();
		}
		return Response.status(202).build();
		
	}

}
