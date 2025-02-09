package ru.fns.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.fns.server.dto.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final JdbcTemplate jdbcTemplate;

    private static final String GET_USER_REQUESTS_SQL = """
            SELECT r.id, r.theme, r.body, r.date, s.status_name AS status
            FROM requests r
            JOIN status_type s ON r.sid = s.id
            ORDER BY r.date DESC 
            LIMIT ? OFFSET ?
        """;

    private static final String UPDATE_REQUEST_STATUS_SQL = """
            UPDATE requests
            SET sid = (SELECT id FROM status_type WHERE status_name = ?)
            WHERE id = ?
        """;

    private static final String GET_ALL_USERS_SQL = """
            SELECT u.id, u.name, u.login, u.email, u.tel, r.authority
            FROM users u
            JOIN user_role r ON u.rid = r.id
        """;

    private static final String UPDATE_USER_SQL = """
            UPDATE users SET name = ?, email = ?, tel = ? WHERE id = ?
        """;

    private static final String GET_ALL_ORGANIZATIONS_SQL = """
            SELECT id, organization_name, inn, kpp, address FROM organizations
        """;

    private static final String UPDATE_ORGANIZATION_SQL = """
            UPDATE organizations SET organization_name = ?, inn = ?, kpp = ?, address = ? WHERE id = ?
        """;

    private static final String DELETE_ORGANIZATION_SQL = """
            DELETE FROM organizations WHERE id = ?
        """;

    private static final String CREATE_ORGANIZATION_SQL = """
            INSERT INTO organizations (id, organization_name, inn, kpp, address) VALUES (?, ?, ?, ?, ?)
        """;

    private static final String GET_ALL_TAX_ASSESSMENTS_SQL = """
            SELECT
                ta.id,
                u.name AS user_name,
                o.organization_name AS organization_name,
                t.tax_name AS tax_type,
                ta.summ,
                ta.paying_deadline,
                s.status_name AS status
            FROM tax_assessments ta
            JOIN users u ON ta.uid = u.id
            JOIN organizations o ON ta.oid = o.id
            JOIN tax_type t ON ta.tid = t.id
            JOIN status_type s ON ta.sid = s.id
            LIMIT ? OFFSET ?;
        """;

    private static final String CREATE_TAX_ASSESSMENT_SQL = """
            INSERT INTO tax_assessments (id, uid, oid, tid, summ, paying_deadline, sid)
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """;

    private static final String UPDATE_TAX_ASSESSMENT_SQL = """
            UPDATE tax_assessments
            SET uid = ?, oid = ?, tid = ?, summ = ?, paying_deadline = ?, sid = ?
            WHERE id = ?;
        """;

    private static final String DELETE_TAX_ASSESSMENT_SQL = """
        DELETE FROM tax_assessments WHERE id = ?
        """;

    public List<Map<String, Object>> getUserRequests(int limit, int offset) {
        return jdbcTemplate.queryForList(GET_USER_REQUESTS_SQL, limit, offset);
    }

    public boolean updateRequestStatus(UUID id, String statusName) {
        int rowsUpdated = jdbcTemplate.update(UPDATE_REQUEST_STATUS_SQL, statusName, id);
        return rowsUpdated > 0;
    }

    public List<Map<String, Object>> getAllUsers() {
        return jdbcTemplate.queryForList(GET_ALL_USERS_SQL);
    }

    public boolean updateUser(UUID id, UserDto user) {
        int rowsAffected = jdbcTemplate.update(UPDATE_USER_SQL, user.getName(), user.getEmail(), user.getTel(), id);
        return rowsAffected > 0;
    }

    public List<Map<String, Object>> getAllOrganizations() {
        return jdbcTemplate.queryForList(GET_ALL_ORGANIZATIONS_SQL);
    }

    public boolean updateOrganization(UUID id, OrganizationDto organization) {
        int rowsAffected = jdbcTemplate.update(UPDATE_ORGANIZATION_SQL,
                organization.getOrganization_name(),
                organization.getInn(),
                organization.getKpp(),
                organization.getAddress(),
                id
        );
        return rowsAffected > 0;
    }

    public boolean deleteOrganization(UUID id) {
        int rowsAffected = jdbcTemplate.update(DELETE_ORGANIZATION_SQL, id);
        return rowsAffected > 0;
    }

    public boolean createOrganization(OrganizationDto organization) {
        int rowsAffected = jdbcTemplate.update(CREATE_ORGANIZATION_SQL,
                UUID.randomUUID(),
                organization.getOrganization_name(),
                organization.getInn(),
                organization.getKpp(),
                organization.getAddress()
        );
        return rowsAffected > 0;
    }

    public List<TaxAssessmentResponseDto> getAllTaxAssessments(int limit, int offset) {
        return jdbcTemplate.query(GET_ALL_TAX_ASSESSMENTS_SQL,
                new BeanPropertyRowMapper<>(TaxAssessmentResponseDto.class),
                limit,
                offset);
    }

    public boolean createTaxAssessment(TaxAssessmentRequestDto taxAssessment) {
        int rowsAffected = jdbcTemplate.update(CREATE_TAX_ASSESSMENT_SQL,
                UUID.randomUUID(),
                getUserId(taxAssessment.getUser_name()),
                getOrganizationId(taxAssessment.getOrganization_name()),
                getTaxTypeId(taxAssessment.getTax_type()),
                taxAssessment.getSumm(),
                taxAssessment.getPaying_deadline(),
                getStatusId(taxAssessment.getStatus()));

        return rowsAffected > 0;
    }

    public boolean updateTaxAssessment(UUID id, TaxAssessmentRequestDto taxAssessment) {
        int rowsAffected = jdbcTemplate.update(UPDATE_TAX_ASSESSMENT_SQL,
                getUserId(taxAssessment.getUser_name()),
                getOrganizationId(taxAssessment.getOrganization_name()),
                getTaxTypeId(taxAssessment.getTax_type()),
                taxAssessment.getSumm(),
                taxAssessment.getPaying_deadline(),
                getStatusId(taxAssessment.getStatus()), id);
        return rowsAffected > 0;
    }

    public boolean deleteTaxAssessment(UUID id) {
        int rowsAffected = jdbcTemplate.update(DELETE_TAX_ASSESSMENT_SQL, id);
        return rowsAffected > 0;
    }

    private UUID getUserId(String userName) {
        String sql = "SELECT id FROM users WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, userName.trim());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private UUID getOrganizationId(String organizationName) {
        String sql = "SELECT id FROM organizations WHERE organization_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, organizationName.trim());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private UUID getTaxTypeId(String taxType) {
        String sql = "SELECT id FROM tax_type WHERE tax_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, taxType.trim());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private UUID getStatusId(String status) {
        String sql = "SELECT id FROM status_type WHERE status_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, UUID.class, status.trim());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
