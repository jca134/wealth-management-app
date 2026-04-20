package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.ClientAdvisorView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientPageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClientPageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClientAdvisorView> findAllClientsWithAdvisors() {
        String sql = """
                SELECT c.client_id, c.name AS client_name, c.email, a.name AS advisor_name
                FROM clients c
                JOIN advisors a ON c.advisor_id = a.advisor_id
                ORDER BY c.client_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClientAdvisorView(
                        rs.getInt("client_id"),
                        rs.getString("client_name"),
                        rs.getString("email"),
                        rs.getString("advisor_name")
                )
        );
    }
}