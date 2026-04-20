package wealthmanagement.wealth_management_app.repository;

import wealthmanagement.wealth_management_app.model.Asset;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AssetRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Asset> rowMapper = (rs, rowNum) -> new Asset(
            rs.getString("symbol"),
            rs.getString("asset_name")
    );

    public AssetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Asset> findAll() {
        return jdbcTemplate.query(
                "SELECT symbol, asset_name FROM assets ORDER BY symbol",
                rowMapper
        );
    }

    public Optional<Asset> findBySymbol(String symbol) {
        return jdbcTemplate.query(
                "SELECT symbol, asset_name FROM assets WHERE symbol = ?",
                rowMapper,
                symbol
        ).stream().findFirst();
    }

    public void insert(Asset asset) {
        jdbcTemplate.update(
                "INSERT INTO assets (symbol, asset_name) VALUES (?, ?)",
                asset.getSymbol(),
                asset.getAssetName()
        );
    }

    public void update(Asset asset) {
        jdbcTemplate.update(
                "UPDATE assets SET asset_name = ? WHERE symbol = ?",
                asset.getAssetName(),
                asset.getSymbol()
        );
    }

    public void delete(String symbol) {
        jdbcTemplate.update("DELETE FROM assets WHERE symbol = ?", symbol);
    }
}
