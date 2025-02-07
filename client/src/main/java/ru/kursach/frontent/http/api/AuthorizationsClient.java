package ru.kursach.frontent.http.api;

import ru.kursach.frontent.http.Client;

import java.io.IOException;

public class AuthorizationsClient extends Client {
    private final String apiUrl = baseUrl + "/auth";

    public String getUser(String login, String password) throws IOException {
        return get(String.format(apiUrl + "?login=%s&password=%s", login, password));
    }
}
