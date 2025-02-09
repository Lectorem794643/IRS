package ru.fns.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_TAX_ASSESSMENTS_SQL = """
        SELECT
            u.name AS user_name,
            o.organization_name AS organization_name,
            ta.summ,
            t.tax_name AS tax_type,
            ta.paying_deadline,
            s.status_name AS status
        FROM tax_assessments ta
        JOIN users u ON ta.uid = u.id
        JOIN organizations o ON ta.oid = o.id
        JOIN tax_type t ON ta.tid = t.id
        JOIN status_type s ON ta.sid = s.id
        WHERE ta.uid = ?
    """;

    private static final String GET_REQUESTS_SQL = """
        SELECT r.theme, r.body, r.date, s.status_name AS status
        FROM requests r
        JOIN status_type s ON r.sid = s.id
        WHERE r.uid = ?
    """;

    private static final String INSERT_REQUEST_SQL = """
        INSERT INTO requests (id, uid, theme, body, date, sid)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    private static final UUID DEFAULT_STATUS_ID = UUID.fromString("ec1bdd0d-a077-42fa-ac4a-4fa46d352c10");

    public List<Map<String, Object>> getTaxAssessmentsByUid(UUID uid) {
        return jdbcTemplate.queryForList(GET_TAX_ASSESSMENTS_SQL, uid);
    }

    public List<Map<String, Object>> getRequestsByUserId(UUID uid) {
        return jdbcTemplate.queryForList(GET_REQUESTS_SQL, uid);
    }

    public boolean createRequest(UUID uid, String theme, String body) {
        UUID id = UUID.randomUUID();
        String date = LocalDate.now().toString();

        return jdbcTemplate.update(INSERT_REQUEST_SQL, id, uid, theme, body, date, DEFAULT_STATUS_ID) > 0;
    }
}
