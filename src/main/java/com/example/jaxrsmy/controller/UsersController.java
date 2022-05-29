package com.example.jaxrsmy.controller;

import com.example.jaxrsmy.dao.UsersDAO;
import com.example.jaxrsmy.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersController {

    private final UsersDAO usersDAO;

    public UsersController() {
        usersDAO = UsersDAO.getInstance();
    }

    @GET
    public Response getAllUsers() {

        throw new RuntimeException("ddddd");

        /*List<User> users = usersDAO.getAllUsers();

        return Response
                .status(Response.Status.OK.getStatusCode())
                .entity(null)
                .build();*/
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") long id) {
        User user = usersDAO.getUserById(id);

        return Response
                .status(Response.Status.OK.getStatusCode())
                .entity(user)
                .build();
    }

    @POST
    public Response addEmployee(User user) {
        usersDAO.addNewUser(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(User user, @PathParam("id") long id) {
        usersDAO.updateUser(user, id);

        return Response
                .status(Response.Status.OK.getStatusCode())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") long id)  {
        usersDAO.deleteUser(id);

        return Response
                .status(Response.Status.OK.getStatusCode())
                .build();
    }

}
