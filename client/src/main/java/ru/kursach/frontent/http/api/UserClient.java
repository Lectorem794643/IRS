package ru.kursach.frontent.http.api;

import ru.kursach.frontent.http.Client;

import java.io.IOException;

public class UserClient extends Client {
    private final String apiUrl = baseUrl + "/user/";

    public String putRequest(Object request) throws IOException {
        return post(apiUrl + "create-request?uid=" + uuid, request);
    }

    public String getRequest() throws IOException {
        return get(apiUrl + "get-requests?uid=" + uuid);
    }

    public String getTax() throws IOException {
        return get(apiUrl + "get-tax?uid=" + uuid);
    }
}
