package com.example.jaxrsmy.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.Set;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(e))
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }

    private String prepareMessage(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            message.append(cv.getMessage() + "\n");
        }
        return message.toString();

    }
}
