package ru.kursach.frontent.http.api;

import ru.kursach.frontent.dto.User;
import ru.kursach.frontent.http.Client;

import java.io.IOException;

public class AdminClient extends Client {
    private final String apiUrl = baseUrl+"/admin/";
    public String getAllUser() throws IOException {
        return get(apiUrl + "get-users-for-name");
    }
    public String getUser(String name) throws IOException {
        return get(apiUrl + "get-users-for-name?name=" + name);
    }

    public String addUser(User user) throws IOException {
        return post(apiUrl + "create-user", user);
    }

    public String updateUser(User selectedPerson) throws IOException {
        return put(apiUrl + "update-user?id="+selectedPerson.getId(), selectedPerson);
    }
    public String deleteUser(User selectedPerson) throws IOException {
        return delete(apiUrl + "delete-user?id="+selectedPerson.getId());
    }
}
