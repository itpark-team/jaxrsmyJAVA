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
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MDC.put("start-time", String.valueOf(System.currentTimeMillis()));

        log.debug("REQUEST Endpoint: /{} ", requestContext.getUriInfo().getPath());
        log.debug("REQUEST Method: {} ", requestContext.getMethod());

        logRequestHeaders(requestContext);

        log.debug("REQUEST Class Processor: {} ", resourceInfo.getResourceClass().getCanonicalName());

        log.debug("REQUEST Method Name Processor: {} ", resourceInfo.getResourceMethod().getName());

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

        logResponseHeaders(responseContext);

        String entity = readResponseEntityStream(responseContext);
        if (entity.length() > 0) {
            log.debug("RESPONSE Entity: {}", entity);
        }

        log.debug("Execution time: {} milliseconds", executionTime);

        MDC.clear();
    }

    private void logRequestHeaders(ContainerRequestContext requestContext) {
        Iterator iterator;
        log.debug("---REQUEST Headers START---");
        iterator = requestContext.getHeaders().keySet().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next().toString();
            String headerValue = requestContext.getHeaderString(headerName);
            log.debug("Header Name: {}, Header Value :{} ",headerName, headerValue);
        }
        log.debug("---REQUEST Headers FINISH---");
    }

    private void logResponseHeaders(ContainerResponseContext responseContext) {
        Iterator iterator;
        log.debug("---RESPONSE Headers START---");
        iterator = responseContext.getHeaders().keySet().iterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next().toString();
            String headerValue = responseContext.getHeaderString(headerName);
            log.debug("Header Name: {}, Header Value :{} ",headerName, headerValue);
        }
        log.debug("---RESPONSE Headers FINISH---");
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
