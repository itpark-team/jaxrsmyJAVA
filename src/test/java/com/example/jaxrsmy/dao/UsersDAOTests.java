package com.example.jaxrsmy.dao;

import com.example.jaxrsmy.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;

public class UsersDAOTests {
    private UsersDAO usersDAO;

    public UsersDAOTests() {
        usersDAO = UsersDAO.getInstance();
    }

    @Test(expected = WebApplicationException.class)
    public void addAlreadyExistUserTest() {
        User user = new User(1, "Иван");
        usersDAO.addNewUser(user);
    }

    @Test(expected = WebApplicationException.class)
    public void getNonExistingUserByIdTest() {
        User user = usersDAO.getUserById(1111);
    }

    @Test
    public void getStartedAllUsersCountTest() {
        int actualSize = usersDAO.getAllUsers().size();
        int expectedSize = 3;

        Assert.assertEquals("Size must be 3", expectedSize, actualSize);
    }


}
