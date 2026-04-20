package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Advisor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AdvisorRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Advisor> rowMapper = (rs, rowNum) -> new Advisor(
            rs.getInt("advisor_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("office_location"),
            rs.getDate("hire_date") == null ? null : rs.getDate("hire_date").toLocalDate()
    );

    public AdvisorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Advisor> findAll() {
        return jdbcTemplate.query(
                "SELECT advisor_id, name, email, phone, office_location, hire_date FROM advisors ORDER BY advisor_id",
                rowMapper
        );
    }

    public Optional<Advisor> findById(int advisorId) {
        return jdbcTemplate.query(
                "SELECT advisor_id, name, email, phone, office_location, hire_date FROM advisors WHERE advisor_id = ?",
                rowMapper,
                advisorId
        ).stream().findFirst();
    }

    public void insert(Advisor a) {
        jdbcTemplate.update(
                "INSERT INTO advisors (name, email, phone, office_location, hire_date) VALUES (?, ?, ?, ?, ?)",
                a.getName(),
                a.getEmail(),
                a.getPhone(),
                a.getOfficeLocation(),
                a.getHireDate() == null ? null : Date.valueOf(a.getHireDate())
        );
    }

    public void update(Advisor a) {
        jdbcTemplate.update(
                "UPDATE advisors SET name = ?, email = ?, phone = ?, office_location = ?, hire_date = ? WHERE advisor_id = ?",
                a.getName(),
                a.getEmail(),
                a.getPhone(),
                a.getOfficeLocation(),
                a.getHireDate() == null ? null : Date.valueOf(a.getHireDate()),
                a.getAdvisorId()
        );
    }

    public void delete(int advisorId) {
        jdbcTemplate.update("DELETE FROM advisors WHERE advisor_id = ?", advisorId);
    }
}
