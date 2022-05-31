package com.example.jaxrsmy.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }


}
