package wealthmanagement.wealth_management_app.model;

import java.time.LocalDate;

public class Account {
    private Integer accountId;
    private Integer clientId;
    private String clientName;
    private String accountType;
    private LocalDate openedDate;
    private String status;

    public Account() {
    }

    public Account(Integer accountId, Integer clientId, String clientName,
                   String accountType, LocalDate openedDate, String status) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.accountType = accountType;
        this.openedDate = openedDate;
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDate openedDate) {
        this.openedDate = openedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
