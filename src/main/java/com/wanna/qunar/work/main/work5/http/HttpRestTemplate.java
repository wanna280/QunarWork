package com.wanna.qunar.work.main.work5.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class HttpRestTemplate {

    public static final String GET_METHOD = "GET";

    public static final int DEFAULT_CONNECTION_TIMEOUT = 3000;

    public static final int DEFAULT_READ_TIMEOUT = 200000;

    public static final int CODE_SUCCESS = 200;

    public String doGet(String getUrl) throws IOException {
        URL url = new URL(getUrl);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET_METHOD);
        connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
        connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
        connection.connect();
        if (connection.getResponseCode() == CODE_SUCCESS) {
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            } finally {
                Optional.ofNullable(inputStream).ifPresent(it -> {
                    try {
                        it.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        final String result = new HttpRestTemplate().doGet("https://www.baidu.com");

    }
}