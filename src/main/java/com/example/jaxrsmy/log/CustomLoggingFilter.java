package com.example.jaxrsmy.log;

import java.io.*;
import java.util.Iterator;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


@Provider
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final Logger log = LoggerFactory.getLogger(CustomLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        MDC.put("start-time", String.valueOf(System.currentTimeMillis()));
        MDC.put("request-entity", readRequestEntityStream(requestContext));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String startTimeInString = MDC.get("start-time");

        if (startTimeInString == null) {
            return;
        }

        long startTime = Long.parseLong(startTimeInString);
        long executionTime = System.currentTimeMillis() - startTime;

        log.debug("REQUEST");
        log.debug("Endpoint: /{} ", requestContext.getUriInfo().getPath());
        log.debug("Method: {} ", requestContext.getMethod());

        logRequestHeaders(requestContext);

        log.debug("Class Processor: {} ", resourceInfo.getResourceClass().getCanonicalName());
        log.debug("Method Processor: {} ", resourceInfo.getResourceMethod().getName());

        String requestEntity = MDC.get("request-entity");
        if (requestEntity.length() > 0) {
            log.debug("Entity: {}", requestEntity);
        }

        log.debug("RESPONSE");
        log.debug("Status: {}", responseContext.getStatus());

        logResponseHeaders(responseContext);

        String responseEntity = readResponseEntityStream(responseContext);
        if (responseEntity.length() > 0) {
            log.debug("Entity: {}", responseEntity);
        }

        log.debug("Execution time: {} milliseconds", executionTime);

        MDC.clear();
    }

    private void logRequestHeaders(ContainerRequestContext requestContext) {
        log.debug("Headers:");

        Iterator iterator = requestContext.getHeaders().keySet().iterator();

        if(!iterator.hasNext()){
            log.debug("\tempty");
            return;
        }

        while (iterator.hasNext()) {
            String headerName = iterator.next().toString();
            String headerValue = requestContext.getHeaderString(headerName);
            log.debug("\t{}: {}", headerName, headerValue);
        }
    }

    private void logResponseHeaders(ContainerResponseContext responseContext) {
        log.debug("Headers:");

        Iterator iterator = responseContext.getHeaders().keySet().iterator();

        if(!iterator.hasNext()){
            log.debug("\tempty");
            return;
        }
        while (iterator.hasNext()) {
            String headerName = iterator.next().toString();
            String headerValue = responseContext.getHeaderString(headerName);
            log.debug("\t{}: {}", headerName, headerValue);
        }
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
