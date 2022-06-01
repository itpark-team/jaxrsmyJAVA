package com.example.jaxrsmy.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NonCaughtExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Something wrong - Non Caught Exception")
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}
