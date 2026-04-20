package wealthmanagement.wealth_management_app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialGoal {
    private Integer goalId;
    private Integer clientId;
    private String clientName;
    private String goalType;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate targetDate;
    private String priority;

    public FinancialGoal() {
    }

    public FinancialGoal(Integer goalId, Integer clientId, String clientName, String goalType,
                         BigDecimal targetAmount, BigDecimal currentAmount, LocalDate targetDate,
                         String priority) {
        this.goalId = goalId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.goalType = goalType;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.targetDate = targetDate;
        this.priority = priority;
    }

    public Integer getGoalId() { return goalId; }
    public void setGoalId(Integer goalId) { this.goalId = goalId; }

    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
