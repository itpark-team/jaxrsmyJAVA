package com.example.jaxrsmy.controller;

import com.example.jaxrsmy.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

public class UsersControllerTests {

    private final String BASE_URL = "http://localhost:8080/api/v1/users";
    private final String NON_EXISTING_USERID = "/1111";
    private final String EXISTING_USERID = "/1";
    private final String UPDATING_USERID = "/3";
    private final String DELETING_USERID = "/2";

    @Test
    public void getAllUsersTest() throws IOException {
        HttpGet request = new HttpGet(BASE_URL);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        String entityInString = EntityUtils.toString(httpResponse.getEntity());
        User[] actualBody = new Gson().fromJson(entityInString, User[].class);

        String actualShortTypeName = actualBody.getClass().getSimpleName();

        int expectedResponseCode = Response.Status.OK.getStatusCode();
        String fullTypeName = new TypeToken<User[]>() {
        }.getType().getTypeName();

        String expectedShortTypeName = fullTypeName.substring(fullTypeName.lastIndexOf(".") + 1);

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Expected body type must be - " + expectedShortTypeName;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedShortTypeName, actualShortTypeName);
    }

    @Test
    public void getNonExistingUserByIdTest() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + NON_EXISTING_USERID);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();
        String actualBody = EntityUtils.toString(httpResponse.getEntity());

        int expectedResponseCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ;
        String expectedBody = "user not found";

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Body must be - " + expectedBody;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedBody, actualBody);
    }

    @Test
    public void getExistingUserByIdTest() throws IOException {
        HttpGet request = new HttpGet(BASE_URL + EXISTING_USERID);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();
        String actualBody = EntityUtils.toString(httpResponse.getEntity());

        int expectedResponseCode = Response.Status.OK.getStatusCode();
        ;
        String expectedBody = "{\"id\":1,\"name\":\"Иван\"}";

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Body must be - " + expectedBody;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedBody, actualBody);
    }

    @Test
    public void addExistingUserTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL);

        User user = new User(1, "Иван");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();
        String actualBody = EntityUtils.toString(httpResponse.getEntity());

        int expectedResponseCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ;
        String expectedBody = "user already exist";

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Body must be - " + expectedBody;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedBody, actualBody);
    }

    @Test
    public void addNotValidUserTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL);

        User user = new User(0, "");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        int expectedResponseCode = Response.Status.BAD_REQUEST.getStatusCode();

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
    }

    @Test
    public void addValidUserTest() throws IOException {
        HttpPost request = new HttpPost(BASE_URL);

        User user = new User(1111, "Пётр");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        request.setEntity(entity);
        request.setHeader("Accept", "application/json");


        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        int expectedResponseCode = Response.Status.CREATED.getStatusCode();

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
    }

    @Test
    public void updateNonExistingUserTest() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + NON_EXISTING_USERID);

        User user = new User(1111, "Иван");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();
        String actualBody = EntityUtils.toString(httpResponse.getEntity());

        int expectedResponseCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ;
        String expectedBody = "user not found";

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Body must be - " + expectedBody;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedBody, actualBody);
    }

    @Test
    public void updateNotValidUserTest() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + EXISTING_USERID);

        User user = new User(0, "");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        int expectedResponseCode = Response.Status.BAD_REQUEST.getStatusCode();

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
    }

    @Test
    public void updateValidUserTest() throws IOException {
        HttpPut request = new HttpPut(BASE_URL + UPDATING_USERID);

        User user = new User(3, "Пётр");

        String json = new Gson().toJson(user);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        int expectedResponseCode = Response.Status.OK.getStatusCode();

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
    }

    @Test
    public void deleteNonExistingUserTest() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + NON_EXISTING_USERID);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();
        String actualBody = EntityUtils.toString(httpResponse.getEntity());

        int expectedResponseCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String expectedBody = "user not found";

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;
        String messageForBodyAssert = "Body must be - " + expectedBody;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
        Assert.assertEquals(messageForBodyAssert, expectedBody, actualBody);
    }

    @Test
    public void deleteExistingUserTest() throws IOException {
        HttpDelete request = new HttpDelete(BASE_URL + DELETING_USERID);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        int actualResponseCode = httpResponse.getStatusLine().getStatusCode();

        int expectedResponseCode = Response.Status.NO_CONTENT.getStatusCode();

        String messageForStatusCodeAssert = "Status code must be - " + expectedResponseCode;

        Assert.assertEquals(messageForStatusCodeAssert, expectedResponseCode, actualResponseCode);
    }
}
