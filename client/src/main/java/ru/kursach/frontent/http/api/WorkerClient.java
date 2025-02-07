package ru.kursach.frontent.http.api;

import ru.kursach.frontent.http.Client;

import java.io.IOException;

public class WorkerClient extends Client {
    private final String url = baseUrl + "/worker/";
    public String getAllOrganizations() throws IOException {
        return get(url + "organizations");
    }
}
