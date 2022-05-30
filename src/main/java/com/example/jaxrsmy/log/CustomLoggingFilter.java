package com.example.jaxrsmy.log;

import java.io.*;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


@Provider
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MDC.put("start-time", String.valueOf(System.currentTimeMillis()));

        log.debug("REQUEST Endpoint: /{} ", requestContext.getUriInfo().getPath());
        log.debug("REQUEST Method: {} ", requestContext.getMethod());

        String entity = readRequestEntityStream(requestContext);
        if (entity.length() > 0) {
            log.debug("REQUEST Entity: {}", entity);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String startTimeInString = MDC.get("start-time");

        if (startTimeInString == null) {
            return;
        }

        long startTime = Long.parseLong(startTimeInString);
        long executionTime = System.currentTimeMillis() - startTime;

        log.debug("RESPONSE Status: {}", responseContext.getStatus());

        String entity = readResponseEntityStream(responseContext);
        if (entity.length() > 0) {
            log.debug("RESPONSE Entity: {}", entity);
        }

        log.debug("Execution time: {} milliseconds", executionTime);

        MDC.clear();
    }

    private String readRequestEntityStream(ContainerRequestContext requestContext) {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        InputStream inputStream = requestContext.getEntityStream();

        StringBuilder builder = new StringBuilder();
        try {
            ReaderWriter.writeTo(inputStream, outputByteArray);
            byte[] requestEntity = outputByteArray.toByteArray();
            if (requestEntity.length != 0) {
                builder.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
        } catch (IOException e) {
            log.debug("----Exception occurred while reading request entity :{}", e.getMessage());
        }
        return builder.toString();
    }

    private String readResponseEntityStream(ContainerResponseContext responseContext) {

        Object entity = responseContext.getEntity();

        if (entity == null) {
            return "";
        }

        String entityInJson = new Gson().toJson(entity);

        return entityInJson;
    }
}
