package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.FinancialGoal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class FinancialGoalRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<FinancialGoal> rowMapper = (rs, rowNum) -> new FinancialGoal(
            rs.getInt("goal_id"),
            rs.getInt("client_id"),
            rs.getString("client_name"),
            rs.getString("goal_type"),
            rs.getBigDecimal("target_amount"),
            rs.getBigDecimal("current_amount"),
            rs.getDate("target_date") == null ? null : rs.getDate("target_date").toLocalDate(),
            rs.getString("priority")
    );

    public FinancialGoalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FinancialGoal> findAll() {
        String sql = """
                SELECT g.goal_id,
                       g.client_id,
                       c.name AS client_name,
                       g.goal_type,
                       g.target_amount,
                       g.current_amount,
                       g.target_date,
                       g.priority
                FROM financial_goals g
                JOIN clients c ON g.client_id = c.client_id
                ORDER BY g.goal_id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<FinancialGoal> findById(int goalId) {
        String sql = """
                SELECT g.goal_id,
                       g.client_id,
                       c.name AS client_name,
                       g.goal_type,
                       g.target_amount,
                       g.current_amount,
                       g.target_date,
                       g.priority
                FROM financial_goals g
                JOIN clients c ON g.client_id = c.client_id
                WHERE g.goal_id = ?
                """;
        return jdbcTemplate.query(sql, rowMapper, goalId).stream().findFirst();
    }

    public void insert(FinancialGoal g) {
        jdbcTemplate.update(
                """
                INSERT INTO financial_goals
                    (client_id, goal_type, target_amount, current_amount, target_date, priority)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                g.getClientId(),
                g.getGoalType(),
                g.getTargetAmount(),
                g.getCurrentAmount(),
                g.getTargetDate() == null ? null : Date.valueOf(g.getTargetDate()),
                g.getPriority()
        );
    }

    public void update(FinancialGoal g) {
        jdbcTemplate.update(
                """
                UPDATE financial_goals
                SET client_id = ?, goal_type = ?, target_amount = ?, current_amount = ?,
                    target_date = ?, priority = ?
                WHERE goal_id = ?
                """,
                g.getClientId(),
                g.getGoalType(),
                g.getTargetAmount(),
                g.getCurrentAmount(),
                g.getTargetDate() == null ? null : Date.valueOf(g.getTargetDate()),
                g.getPriority(),
                g.getGoalId()
        );
    }

    public void delete(int goalId) {
        jdbcTemplate.update("DELETE FROM financial_goals WHERE goal_id = ?", goalId);
    }
}
