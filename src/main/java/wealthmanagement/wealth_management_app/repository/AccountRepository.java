package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Account;
import wealthmanagement.wealth_management_app.model.AccountOption;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> rowMapper = (rs, rowNum) -> new Account(
            rs.getInt("account_id"),
            rs.getInt("client_id"),
            rs.getString("client_name"),
            rs.getString("account_type"),
            rs.getDate("opened_date") == null ? null : rs.getDate("opened_date").toLocalDate(),
            rs.getString("status")
    );

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> findAll() {
        String sql = """
                SELECT a.account_id,
                       a.client_id,
                       c.name AS client_name,
                       a.account_type,
                       a.opened_date,
                       a.status
                FROM accounts a
                JOIN clients c ON a.client_id = c.client_id
                ORDER BY a.account_id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Account> findById(int accountId) {
        String sql = """
                SELECT a.account_id,
                       a.client_id,
                       c.name AS client_name,
                       a.account_type,
                       a.opened_date,
                       a.status
                FROM accounts a
                JOIN clients c ON a.client_id = c.client_id
                WHERE a.account_id = ?
                """;
        return jdbcTemplate.query(sql, rowMapper, accountId).stream().findFirst();
    }

    public void insert(Account account) {
        jdbcTemplate.update(
                "INSERT INTO accounts (client_id, account_type, opened_date, status) VALUES (?, ?, ?, ?)",
                account.getClientId(),
                account.getAccountType(),
                account.getOpenedDate() == null ? null : Date.valueOf(account.getOpenedDate()),
                account.getStatus()
        );
    }

    public void update(Account account) {
        jdbcTemplate.update(
                "UPDATE accounts SET client_id = ?, account_type = ?, opened_date = ?, status = ? WHERE account_id = ?",
                account.getClientId(),
                account.getAccountType(),
                account.getOpenedDate() == null ? null : Date.valueOf(account.getOpenedDate()),
                account.getStatus(),
                account.getAccountId()
        );
    }

    public void delete(int accountId) {
        jdbcTemplate.update("DELETE FROM accounts WHERE account_id = ?", accountId);
    }

    public List<AccountOption> findAllAccountOptions() {
        String sql = """
                SELECT a.account_id, a.account_type, c.name AS client_name
                FROM accounts a
                JOIN clients c ON a.client_id = c.client_id
                ORDER BY a.account_id
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new AccountOption(
                        rs.getInt("account_id"),
                        "#" + rs.getInt("account_id") + " - " + rs.getString("client_name")
                                + " (" + rs.getString("account_type") + ")"
                ));
    }
}
