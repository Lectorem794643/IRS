package ru.fns.server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String GET_ROLE_ID_SQL = """
    SELECT id FROM user_role WHERE authority = ?
    """;
    private static final String GET_USERS_SQL = """
        SELECT u.id, u.name, r.authority, u.login, u.email, u.tel
        FROM users u 
        JOIN user_role r ON u.rid = r.id
        WHERE u.name ILIKE ?
        LIMIT ? OFFSET ?
    """;
    private static final String INSERT_USER_SQL = """
        INSERT INTO users (id, name, rid, login, email, tel, password) 
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
    private static final String UPDATE_USER_SQL = """
        UPDATE users 
        SET name = ?, rid = ?, login = ?, email = ?, tel = ? 
        WHERE id = ?
    """;
    private static final String DELETE_USER_SQL = """
    DELETE FROM users WHERE id = ?
    """;
    private static final String RESET_PASSWORD_SQL = """
    UPDATE users SET password = ? WHERE id = ?
    """;

    public List<Map<String, Object>> getUsersForName(int limit, int offset, String name) {
        return jdbcTemplate.queryForList(GET_USERS_SQL, "%" + name + "%", limit, offset);
    }

    public boolean createUser(String name, String authority, String login, String email, String tel) {
        UUID roleId = getRoleIdByAuthority(authority).orElseThrow(() ->
                new IllegalArgumentException("Invalid authority: " + authority));

        UUID userId = UUID.randomUUID();
        String hashedPassword = passwordEncoder.encode("qwerty123"); // Хэшируем пароль перед сохранением

        return jdbcTemplate.update(INSERT_USER_SQL, userId, name, roleId, login, email, tel, hashedPassword) > 0;
    }

    public boolean updateUser(UUID userId, String name, String authority, String login, String email, String tel) {
        UUID roleId = getRoleIdByAuthority(authority).orElseThrow(() ->
                new IllegalArgumentException("Invalid authority: " + authority));

        return jdbcTemplate.update(UPDATE_USER_SQL, name, roleId, login, email, tel, userId) > 0;
    }

    public boolean deleteUser(UUID userId) {
        return jdbcTemplate.update(DELETE_USER_SQL, userId) > 0;
    }

    public boolean resetPassword(UUID userId) {
        String hashedPassword = passwordEncoder.encode("qwerty123"); // Хэшируем перед сбросом
        return jdbcTemplate.update(RESET_PASSWORD_SQL, hashedPassword, userId) > 0;
    }

    private Optional<UUID> getRoleIdByAuthority(String authority) {
        return Optional.of(jdbcTemplate.queryForObject(GET_ROLE_ID_SQL, UUID.class, authority));
    }
}


