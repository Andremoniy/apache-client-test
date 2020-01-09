package com.github.andremoniy.apacheclienttest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FailoverTest {

    @Test
    void shouldFailoverToAnotherIp() throws IOException {
        // Given

        // When
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet("https://www.twitter.com");
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                System.out.println(response.getStatusLine());
            }
        }

        // Then
    }

}
