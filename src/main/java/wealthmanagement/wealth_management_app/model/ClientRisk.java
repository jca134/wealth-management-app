package wealthmanagement.wealth_management_app.model;

import java.time.LocalDate;

public class ClientRisk {
    private Integer assessmentId;
    private Integer clientId;
    private String clientName;
    private String profileName;
    private Integer riskScore;
    private LocalDate assessmentDate;
    private String method;

    public ClientRisk() {
    }

    public ClientRisk(Integer assessmentId, Integer clientId, String clientName,
                      String profileName, Integer riskScore, LocalDate assessmentDate, String method) {
        this.assessmentId = assessmentId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.profileName = profileName;
        this.riskScore = riskScore;
        this.assessmentDate = assessmentDate;
        this.method = method;
    }

    public Integer getAssessmentId() { return assessmentId; }
    public void setAssessmentId(Integer assessmentId) { this.assessmentId = assessmentId; }

    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }

    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
