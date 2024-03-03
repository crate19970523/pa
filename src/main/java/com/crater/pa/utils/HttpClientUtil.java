package com.crater.pa.utils;

import com.crater.pa.exception.PaHttpClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static <T> T sendPostHttpByJson(String url, Map<String, String> requestHeader, Object requestBody,
                                           Class<T> responseClass) {
        try (HttpClient httpClient = HttpClient.newHttpClient()){
            writeRequestLog(url, requestHeader, requestBody);
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url))
                    .headers(generateHeaderArray(requestHeader)).POST(requestBody == null ?
                            HttpRequest.BodyPublishers.noBody() :
                            HttpRequest.BodyPublishers.ofString(objectConverterToJson(requestBody).orElseThrow(() ->
                                    new PaHttpClientException("requestBody error")))).build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            writeResponse(response.body());
            return String.class.equals(responseClass) ? (T) response.body() : jsonToObject(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            throw new PaHttpClientException("send request error");
        } catch (PaHttpClientException e) {
            throw e;
        } catch (Exception e) {
            throw new PaHttpClientException("send post request have unknown error", e);
        }
    }

    public static <T> T sendGetHttp(String url, Map<String, String> requestHeaderInfo, Class<T> responseClass) {
        writeRequestLog(url, requestHeaderInfo, null);
        var httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest;
        if (requestHeaderInfo == null) httpRequest = HttpRequest.newBuilder(URI.create(url)).GET().build();
        else httpRequest = HttpRequest.newBuilder(URI.create(url))
                .headers(generateHeaderArray(requestHeaderInfo)).GET().build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            writeResponse(response.body());
            return String.class.equals(responseClass) ? (T) response.body() : jsonToObject(response.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            throw new PaHttpClientException("send request error");
        }
    }

    private static void writeRequestLog(String url, Map<String, String> requestHeader, Object requestBody) {
        try {
            var requestLog = new StringBuilder();
            requestLog.append(url).append("\n");
            if (requestHeader != null) {
                requestHeader.forEach((k, v) -> requestLog.append(k).append(" : ").append(v).append("\n"));
            }
            requestLog.append("body: ").append(new Gson().toJson(requestBody));
            log.info(requestLog.toString());
        } catch (Exception e) {
            log.error("write request log fail", e);
        }
    }

    private static void writeResponse(String responseBody) {
        log.info("response: " + responseBody);
    }

    private static String[] generateHeaderArray(Map<String, String> requestHeaderInfo) {
        try {
            var headers = new ArrayList<String>();
            headers.add("Content-Type");
            headers.add("application/json");
            headers.add("Accept");
            headers.add("application/json");
            if (Objects.nonNull(requestHeaderInfo)) {
                requestHeaderInfo.forEach((k, v) -> {
                    headers.add(k);
                    headers.add(v);
                });
            }
            return headers.toArray(String[]::new);
        } catch (Exception e) {
            throw new PaHttpClientException("generate header fail", e);
        }
    }

    private static Optional<String> objectConverterToJson(Object request) {
        Optional<String> result = Optional.empty();
        var objectMapper = new ObjectMapper();
        try {
            result = Optional.ofNullable(objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    private static <T> T jsonToObject(String json, Class<T> targetClass) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (Exception e) {
            throw new PaHttpClientException("response converter to object fail", e);
        }
    }
}
