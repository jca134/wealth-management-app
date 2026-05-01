package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Client;
import wealthmanagement.wealth_management_app.model.ClientAdvisorView;
import wealthmanagement.wealth_management_app.model.ClientOption;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientPageRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> new Client(
            rs.getInt("client_id"),
            rs.getInt("advisor_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getDate("dob") == null ? null : rs.getDate("dob").toLocalDate()
    );

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

    public List<ClientOption> findAllClientOptions() {
        String sql = """
                SELECT client_id, name
                FROM clients
                ORDER BY name
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClientOption(rs.getInt("client_id"), rs.getString("name"))
        );
    }

    public Optional<Client> findById(int clientId) {
        return jdbcTemplate.query(
                "SELECT client_id, advisor_id, name, email, phone, address, dob FROM clients WHERE client_id = ?",
                clientRowMapper,
                clientId
        ).stream().findFirst();
    }

    public void insert(Client c) {
        Integer nextId = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(client_id), 0) + 1 FROM clients", Integer.class);

        jdbcTemplate.update(
                "INSERT INTO clients (client_id, advisor_id, name, email, phone, address, dob) VALUES (?, ?, ?, ?, ?, ?, ?)",
                nextId,
                c.getAdvisorId(),
                c.getName(),
                c.getEmail(),
                c.getPhone(),
                c.getAddress(),
                c.getDob() == null ? null : Date.valueOf(c.getDob())
        );
    }

    public void update(Client c) {
        jdbcTemplate.update(
                "UPDATE clients SET advisor_id = ?, name = ?, email = ?, phone = ?, address = ?, dob = ? WHERE client_id = ?",
                c.getAdvisorId(),
                c.getName(),
                c.getEmail(),
                c.getPhone(),
                c.getAddress(),
                c.getDob() == null ? null : Date.valueOf(c.getDob()),
                c.getClientId()
        );
    }

    public void delete(int clientId) {
        jdbcTemplate.update("DELETE FROM clients WHERE client_id = ?", clientId);
    }
}
