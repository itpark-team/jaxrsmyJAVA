package com.example.jaxrsmy.controller;

import com.example.jaxrsmy.dao.UsersDAO;
import com.example.jaxrsmy.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {

    private final UsersDAO usersDAO;

    public UsersController() {
        usersDAO = new UsersDAO();
    }

    @GET
    public List<User> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    @GET
    @Path("{id}")
    public User getUserById(@PathParam("id") long id) throws Exception {
        return usersDAO.getUserById(id);
    }
}
