package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.ClientRisk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRiskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ClientRisk> rowMapper = (rs, rowNum) -> new ClientRisk(
            rs.getInt("assessment_id"),
            rs.getInt("client_id"),
            rs.getString("client_name"),
            rs.getString("profile_name"),
            rs.getInt("risk_score"),
            rs.getDate("assessment_date") == null ? null : rs.getDate("assessment_date").toLocalDate(),
            rs.getString("method")
    );

    public ClientRiskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClientRisk> findAll() {
        String sql = """
                SELECT r.assessment_id,
                       r.client_id,
                       c.name AS client_name,
                       r.profile_name,
                       r.risk_score,
                       r.assessment_date,
                       r.method
                FROM client_risk_assessments r
                JOIN clients c ON r.client_id = c.client_id
                ORDER BY r.assessment_id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<ClientRisk> findById(int assessmentId) {
        String sql = """
                SELECT r.assessment_id,
                       r.client_id,
                       c.name AS client_name,
                       r.profile_name,
                       r.risk_score,
                       r.assessment_date,
                       r.method
                FROM client_risk_assessments r
                JOIN clients c ON r.client_id = c.client_id
                WHERE r.assessment_id = ?
                """;
        return jdbcTemplate.query(sql, rowMapper, assessmentId).stream().findFirst();
    }

    public void insert(ClientRisk risk) {
        jdbcTemplate.update(
                """
                INSERT INTO client_risk_assessments
                    (client_id, profile_name, risk_score, assessment_date, method)
                VALUES (?, ?, ?, ?, ?)
                """,
                risk.getClientId(),
                risk.getProfileName(),
                risk.getRiskScore(),
                risk.getAssessmentDate() == null ? null : Date.valueOf(risk.getAssessmentDate()),
                risk.getMethod()
        );
    }

    public void update(ClientRisk risk) {
        jdbcTemplate.update(
                """
                UPDATE client_risk_assessments
                SET client_id = ?, profile_name = ?, risk_score = ?, assessment_date = ?, method = ?
                WHERE assessment_id = ?
                """,
                risk.getClientId(),
                risk.getProfileName(),
                risk.getRiskScore(),
                risk.getAssessmentDate() == null ? null : Date.valueOf(risk.getAssessmentDate()),
                risk.getMethod(),
                risk.getAssessmentId()
        );
    }

    public void delete(int assessmentId) {
        jdbcTemplate.update("DELETE FROM client_risk_assessments WHERE assessment_id = ?", assessmentId);
    }

    public List<String> findAllProfileNames() {
        return jdbcTemplate.queryForList(
                "SELECT profile_name FROM risk_profiles ORDER BY profile_name",
                String.class
        );
    }
}
