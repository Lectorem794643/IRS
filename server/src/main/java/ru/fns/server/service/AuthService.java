package ru.fns.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.fns.server.dto.AuthResponse;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String AUTH_QUERY = """
        SELECT u.id, u.password, r.authority
        FROM users u
        JOIN user_role r ON u.rid = r.id
        WHERE u.login = ?
    """;

    public AuthResponse authorize(String login, String password) {
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(AUTH_QUERY, login);
            String storedPasswordHash = (String) result.get("password");

            // Проверяем введённый пароль с хэшированным значением в БД
            if (passwordEncoder.matches(password, storedPasswordHash)) {
                UUID id = (UUID) result.get("id");
                String authority = (String) result.get("authority");
                return new AuthResponse(id, authority);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Invalid login credentials");
        }

        return new AuthResponse(null, null);
    }
}
