package wealthmanagement.wealth_management_app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private Integer txnId;
    private Integer accountId;
    private String accountLabel;
    private String symbol;
    private String txnType;
    private BigDecimal quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal fees;
    private LocalDate tradeDate;
    private LocalDate settleDate;

    public Transaction() {
    }

    public Transaction(Integer txnId, Integer accountId, String accountLabel, String symbol,
                       String txnType, BigDecimal quantity, BigDecimal pricePerUnit, BigDecimal fees,
                       LocalDate tradeDate, LocalDate settleDate) {
        this.txnId = txnId;
        this.accountId = accountId;
        this.accountLabel = accountLabel;
        this.symbol = symbol;
        this.txnType = txnType;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.fees = fees;
        this.tradeDate = tradeDate;
        this.settleDate = settleDate;
    }

    public Integer getTxnId() { return txnId; }
    public void setTxnId(Integer txnId) { this.txnId = txnId; }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }

    public String getAccountLabel() { return accountLabel; }
    public void setAccountLabel(String accountLabel) { this.accountLabel = accountLabel; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    public BigDecimal getFees() { return fees; }
    public void setFees(BigDecimal fees) { this.fees = fees; }

    public LocalDate getTradeDate() { return tradeDate; }
    public void setTradeDate(LocalDate tradeDate) { this.tradeDate = tradeDate; }

    public LocalDate getSettleDate() { return settleDate; }
    public void setSettleDate(LocalDate settleDate) { this.settleDate = settleDate; }
}
