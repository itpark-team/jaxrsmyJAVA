package com.example.jaxrsmy.dao;

import com.example.jaxrsmy.model.User;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;

public class UsersDAOTest {
    private final UsersDAO usersDAO = UsersDAO.getInstance();

    private final long NON_EXISTING_USERID = 1111;
    private final long NON_EXISTING_GETING_USERID = 11111;
    private final long EXISTING_USERID = 1;
    private final long EXISTING_UPDATING_USERID = 3;
    private final long NON_EXISTING_UPDATING_USERID = 33;
    private final long EXISTING_DELETING_USERID = 2;
    private final long NON_EXISTING_DELETING_USERID = 22;

    @Test
    public void getStartedAllUsersCountTest() {
        int actualSize = usersDAO.getAllUsers().size();

        boolean expectedResult = actualSize > 0;

        String messageForExpectedSizeAssert = "Size must be more than 0";

        Assert.assertTrue(messageForExpectedSizeAssert, expectedResult);
    }

    @Test(expected = WebApplicationException.class)
    public void getNonExistingUserByIdTest() {
        User user = usersDAO.getUserById(NON_EXISTING_GETING_USERID);
    }

    @Test
    public void getExistingUserByIdTest() {
        User user = usersDAO.getUserById(EXISTING_USERID);

        String actualUserName = user.getName();
        String expectedUserName = "Иван";

        String messageForUserNameAssert = "User Name must be - " + expectedUserName;

        Assert.assertEquals(messageForUserNameAssert, expectedUserName, actualUserName);
    }

    @Test(expected = WebApplicationException.class)
    public void addExistingUserTest() {
        User user = new User(EXISTING_USERID, "Иван");
        usersDAO.addNewUser(user);
    }

    @Test
    public void addNonExistingUserTest() {
        User user = new User(NON_EXISTING_USERID, "Иван");
        usersDAO.addNewUser(user);
    }

    @Test(expected = WebApplicationException.class)
    public void updateNonExistingUserTest() {
        User user = new User(NON_EXISTING_UPDATING_USERID, "Иван");
        usersDAO.updateUser(user, NON_EXISTING_UPDATING_USERID);
    }

    @Test
    public void updateExistingUserTest() {
        User user = new User(EXISTING_UPDATING_USERID, "Пётр");
        usersDAO.updateUser(user, EXISTING_UPDATING_USERID);
    }

    @Test(expected = WebApplicationException.class)
    public void deleteNonExistingUserTest() {
        usersDAO.deleteUser(NON_EXISTING_DELETING_USERID);
    }

    @Test
    public void deleteExistingUserTest() {
        usersDAO.deleteUser(EXISTING_DELETING_USERID);
    }


}
