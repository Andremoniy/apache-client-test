package com.github.andremoniy.apacheclienttest;

import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FailoverTest {

    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 2000;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println("Running " + testInfo.getTestMethod().get().getName()+"...");
    }

    @Test
    void shouldFailoverToAnotherIpWithApacheClient() throws IOException {
        // Given

        // When
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://www.twitter.com");
            final RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                    .setConnectTimeout(CONNECTION_TIMEOUT)
                    .setSocketTimeout(CONNECTION_TIMEOUT)
                    .build();
            httpGet.setConfig(requestConfig);
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                // Then
                final StatusLine statusLine = response.getStatusLine();
                System.out.println("Status line (Apache client): " + statusLine);
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
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(READ_TIMEOUT);

        // When
        urlConnection.connect();

        final int responseCode = urlConnection.getResponseCode();
        System.out.println("Response code (URLConnection): " + responseCode);
        assertEquals(200, responseCode);
    }

}
