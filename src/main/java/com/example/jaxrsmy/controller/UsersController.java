package com.example.jaxrsmy.controller;

import com.example.jaxrsmy.dao.UsersDAO;
import com.example.jaxrsmy.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.net.URI;
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
        List<User> users = usersDAO.getAllUsers();

        return Response
                .status(Response.Status.OK.getStatusCode())
                .entity(users)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") long id) throws Exception {
        User user = usersDAO.getUserById(id);

        return Response
                .status(Response.Status.OK.getStatusCode())
                .entity(user)
                .build();
    }

    @POST
    public Response addEmployee(User user) throws Exception {
        usersDAO.addNewUser(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(User user, @PathParam("id") long id) throws Exception {
        usersDAO.updateUser(user, id);

        return Response
                .status(Response.Status.OK.getStatusCode())
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") long id) throws Exception {
        usersDAO.deleteUser(id);

        return Response
                .status(Response.Status.NO_CONTENT.getStatusCode())
                .build();
    }

}
