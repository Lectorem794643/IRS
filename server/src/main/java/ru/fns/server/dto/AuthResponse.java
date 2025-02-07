package ru.fns.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class AuthResponse {
    private UUID id;
    private String role;

    public AuthResponse(UUID id, String role) {
        this.id = id;
        this.role = role;
    }
}
