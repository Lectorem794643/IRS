package ru.kursach.frontent.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class Client {
    private static final String APPLICATION_JSON = "application/json";
    private final ObjectMapper objectMapper;
    protected UUID uuid;
    protected String baseUrl = "http://localhost:8080";

    public Client() {
        this.objectMapper = new ObjectMapper();
    }

    public String post(String url, Object object) throws IOException {
        return sendRequestWithBody(url, "POST", object);
    }

    public String put(String url, Object object) throws IOException {
        return sendRequestWithBody(url, "PUT", object);
    }

    public String get(String url) throws IOException {
        HttpURLConnection connection = createConnection(url, "GET", Map.of(
                "Accept", APPLICATION_JSON
        ));
        return executeRequest(connection);
    }

    public String delete(String url) throws IOException {
        HttpURLConnection connection = createConnection(url, "DELETE", Map.of(
                "Accept", APPLICATION_JSON
        ));
        return executeRequest(connection);
    }

    private String sendRequestWithBody(String url, String method, Object object) throws IOException {
        HttpURLConnection connection = createConnection(url, method, Map.of(
                "Content-Type", APPLICATION_JSON,
                "Accept", APPLICATION_JSON
        ));
        connection.setDoOutput(true);

        String json = objectMapper.writeValueAsString(object);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        return executeRequest(connection);
    }

    private HttpURLConnection createConnection(String url, String method, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        headers.forEach(connection::setRequestProperty);
        return connection;
    }

    private String executeRequest(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        String response = readResponse(connection, status);

        if (status < 200 || status >= 300) {
            throw new IOException("Request failed with status: " + status + " - " + response);
        }

        return response;
    }

    private String readResponse(HttpURLConnection connection, int status) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(
                status >= 200 && status < 300 ? connection.getInputStream() : connection.getErrorStream(),
                StandardCharsets.UTF_8);
             BufferedReader in = new BufferedReader(isr)) {

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
