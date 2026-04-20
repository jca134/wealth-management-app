package wealthmanagement.wealth_management_app.model;

public class Asset {
    private String symbol;
    private String assetName;

    public Asset() {
    }

    public Asset(String symbol, String assetName) {
        this.symbol = symbol;
        this.assetName = assetName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
}
