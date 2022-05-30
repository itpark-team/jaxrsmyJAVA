package com.example.jaxrsmy.controller;

import com.example.jaxrsmy.model.User;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class UsersControllerTests {

    private final String BASE_URL = "http://localhost:8080/api/v1/users";

    @Test
    public void getStatusForAllUsersMethodTest() throws IOException {
        HttpUriRequest request = new HttpGet(BASE_URL);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actual = httpResponse.getStatusLine().getStatusCode();
        int expected = 200;

        Assert.assertEquals("Status code must be 200", expected, actual);
    }

    @Test
    public void getExitingUserByIdTest() throws IOException {
        HttpUriRequest request = new HttpGet(BASE_URL + "/1");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String userInJson = EntityUtils.toString(httpResponse.getEntity());
        User user = new Gson().fromJson(userInJson, User.class);

        Assert.assertNotNull("User must be NON null", user);
    }
}
