package wealthmanagement.wealth_management_app.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ExamplesController {

    private static final int MAX_ROWS = 200;

    private final JdbcTemplate jdbcTemplate;

    public ExamplesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/examples")
    public String examples() {
        return "examples";
    }

    @PostMapping("/examples/run")
    public String run(@RequestParam("sql") String sql,
                      @RequestParam(value = "exampleId", required = false) String exampleId,
                      Model model) {
        model.addAttribute("submittedSql", sql);
        model.addAttribute("exampleId", exampleId);

        String trimmed = sql == null ? "" : sql.trim();
        if (trimmed.isEmpty()) {
            model.addAttribute("error", "Please enter a SQL query.");
            return "examples";
        }

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(trimmed);
            List<String> columns = new ArrayList<>();
            if (!rows.isEmpty()) {
                columns.addAll(rows.get(0).keySet());
            }
            List<Map<String, Object>> limited = rows.size() > MAX_ROWS
                    ? rows.subList(0, MAX_ROWS)
                    : rows;

            model.addAttribute("columns", columns);
            model.addAttribute("rows", limited);
            model.addAttribute("rowCount", rows.size());
            model.addAttribute("truncated", rows.size() > MAX_ROWS);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("columns", new ArrayList<String>());
            model.addAttribute("rows", new ArrayList<Map<String, Object>>());
        }

        return "examples";
    }
}
