package com.github.andremoniy.apacheclienttest;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailoverTest {

    @Test
    void shouldFailoverToAnotherIpWithApacheClient() throws IOException {
        // Given

        // When
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet("https://www.twitter.com");
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                // Then
                final StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine);
                assertEquals(200, statusLine.getStatusCode());
            }
        }
    }

    @Test
    void testFailoverWithURL() throws IOException {
        // Given
        final URL url = new URL("https://www.twitter.com");
        final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        // When
        urlConnection.connect();

        final int responseCode = urlConnection.getResponseCode();
        System.out.println(responseCode);
        assertEquals(200, responseCode);
    }

}
