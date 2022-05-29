package com.example.jaxrsmy.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionHandler implements ExceptionMapper {
//    @Override
//    public Response toResponse(MyException e) {
//        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
//    }

    @Override
    public Response toResponse(Throwable throwable) {
        JsonExp error = new JsonExp(throwable.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
