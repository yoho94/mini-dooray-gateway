package com.nhn.minidooray.gateway.util;

import com.nhn.minidooray.gateway.domain.response.ApiResultResponse;
import com.nhn.minidooray.gateway.exception.ApiException;
import com.nhn.minidooray.gateway.exception.BadRequestException;
import com.nhn.minidooray.gateway.exception.WebException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ApiCallUtil {
    private ApiCallUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Util Class!");
    }

    public static <T, E> @NonNull ApiResultResponse<T> getWithBody(ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, E body, Object... uriVariables) {
        return exec(type, HttpMethod.GET, restTemplate, url, body, uriVariables);
    }

    public static <T> @NonNull ApiResultResponse<T> get(ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, Object... uriVariables) {
        return exec(type, HttpMethod.GET, restTemplate, url, null, uriVariables);
    }

    public static <T, E> @NonNull ApiResultResponse<T> postWithBody(ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, E body, Object... uriVariables) {
        return exec(type, HttpMethod.POST, restTemplate, url, body, uriVariables);
    }

    public static <T> @NonNull ApiResultResponse<T> post(ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, Object... uriVariables) {
        return exec(type, HttpMethod.POST, restTemplate, url, null, uriVariables);
    }

    public static <T, E> @NonNull ApiResultResponse<T> callWithBody(HttpMethod httpMethod, ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, E body, Object... uriVariables) {
        return exec(type, httpMethod, restTemplate, url, body, uriVariables);
    }

    public static <T> @NonNull ApiResultResponse<T> call(HttpMethod httpMethod, ParameterizedTypeReference<ApiResultResponse<T>> type, RestTemplate restTemplate, String url, Object... uriVariables) {
        return exec(type, httpMethod, restTemplate, url, null, uriVariables);
    }

    private static <T, E> @NonNull ApiResultResponse<T> exec(ParameterizedTypeReference<ApiResultResponse<T>> type, HttpMethod httpMethod, RestTemplate restTemplate, String url, E body, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<E> requestEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<ApiResultResponse<T>> exchange = restTemplate.exchange(url,
                httpMethod,
                requestEntity,
                type,
                uriVariables);

        ApiResultResponse<T> exchangeBody = exchange.getBody();

        if (exchangeBody == null) {
            throw new ApiException();
        }

        ApiResultResponse.Header header = exchangeBody.getHeader();

        if (header == null) {
            throw new ApiException();
        }

        if (header.getResultCode() == HttpStatus.BAD_REQUEST.value()) {
            throw new BadRequestException(header.getResultMessage());
        }

        if (!header.isSuccessful()) {
            throw new WebException("API ERROR : " + header.getResultMessage(), HttpStatus.valueOf(header.getResultCode()));
        }

        return exchangeBody;
    }
}
