package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Transaction> rowMapper = (rs, rowNum) -> {
        int accountId = rs.getInt("account_id");
        String clientName = rs.getString("client_name");
        String label = "#" + accountId + (clientName == null ? "" : " - " + clientName);
        return new Transaction(
                rs.getInt("txn_id"),
                accountId,
                label,
                rs.getString("symbol"),
                rs.getString("txn_type"),
                rs.getBigDecimal("quantity"),
                rs.getBigDecimal("price_per_unit"),
                rs.getBigDecimal("fees"),
                rs.getDate("trade_date") == null ? null : rs.getDate("trade_date").toLocalDate(),
                rs.getDate("settle_date") == null ? null : rs.getDate("settle_date").toLocalDate()
        );
    };

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> findAll() {
        String sql = """
                SELECT t.txn_id,
                       t.account_id,
                       c.name AS client_name,
                       t.symbol,
                       t.txn_type,
                       t.quantity,
                       t.price_per_unit,
                       t.fees,
                       t.trade_date,
                       t.settle_date
                FROM transactions t
                JOIN accounts a ON t.account_id = a.account_id
                JOIN clients c ON a.client_id = c.client_id
                ORDER BY t.trade_date DESC, t.txn_id DESC
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Transaction> findById(int txnId) {
        String sql = """
                SELECT t.txn_id,
                       t.account_id,
                       c.name AS client_name,
                       t.symbol,
                       t.txn_type,
                       t.quantity,
                       t.price_per_unit,
                       t.fees,
                       t.trade_date,
                       t.settle_date
                FROM transactions t
                JOIN accounts a ON t.account_id = a.account_id
                JOIN clients c ON a.client_id = c.client_id
                WHERE t.txn_id = ?
                """;
        return jdbcTemplate.query(sql, rowMapper, txnId).stream().findFirst();
    }

    public void insert(Transaction t) {
        jdbcTemplate.update(
                """
                INSERT INTO transactions
                    (account_id, symbol, txn_type, quantity, price_per_unit, fees, trade_date, settle_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                t.getAccountId(),
                t.getSymbol(),
                t.getTxnType(),
                t.getQuantity(),
                t.getPricePerUnit(),
                t.getFees(),
                t.getTradeDate() == null ? null : Date.valueOf(t.getTradeDate()),
                t.getSettleDate() == null ? null : Date.valueOf(t.getSettleDate())
        );
    }

    public void update(Transaction t) {
        jdbcTemplate.update(
                """
                UPDATE transactions
                SET account_id = ?, symbol = ?, txn_type = ?, quantity = ?,
                    price_per_unit = ?, fees = ?, trade_date = ?, settle_date = ?
                WHERE txn_id = ?
                """,
                t.getAccountId(),
                t.getSymbol(),
                t.getTxnType(),
                t.getQuantity(),
                t.getPricePerUnit(),
                t.getFees(),
                t.getTradeDate() == null ? null : Date.valueOf(t.getTradeDate()),
                t.getSettleDate() == null ? null : Date.valueOf(t.getSettleDate()),
                t.getTxnId()
        );
    }

    public void delete(int txnId) {
        jdbcTemplate.update("DELETE FROM transactions WHERE txn_id = ?", txnId);
    }
}
