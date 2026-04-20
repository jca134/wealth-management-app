package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Client;
import wealthmanagement.wealth_management_app.model.ClientAdvisorView;
import wealthmanagement.wealth_management_app.model.ClientOption;
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
                SELECT c.client_id,
                       c.name AS client_name,
                       c.email,
                       a.name AS advisor_name
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

    public void addClient(Client client) {
        String sql = """
                INSERT INTO clients (client_id, advisor_id, name, email, phone, address, dob)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                client.getClientId(),
                client.getAdvisorId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                java.sql.Date.valueOf(client.getDob()));
    }

    public void updateClient(Client client) {
        String sql = """
                UPDATE clients
                SET advisor_id = ?, name = ?, email = ?, phone = ?, address = ?, dob = ?
                WHERE client_id = ?
                """;

        jdbcTemplate.update(sql,
                client.getAdvisorId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                java.sql.Date.valueOf(client.getDob()),
                client.getClientId());
    }

    public void deleteClient(int clientId) {
        String sql = "DELETE FROM clients WHERE client_id = ?";
        jdbcTemplate.update(sql, clientId);
    }

    public List<ClientOption> findAllClientOptions() {
        String sql = "SELECT client_id, name FROM clients ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClientOption(rs.getInt("client_id"), rs.getString("name")));
    }

    public Client findClientById(int clientId) {
        String sql = """
                SELECT client_id, advisor_id, name, email, phone, address, dob
                FROM clients
                WHERE client_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Client(
                        rs.getInt("client_id"),
                        rs.getInt("advisor_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("dob").toLocalDate()
                ), clientId);
    }
}