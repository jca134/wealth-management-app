package wealthmanagement.wealth_management_app.model;

public class ClientAdvisorView {
    private int clientId;
    private String clientName;
    private String email;
    private String advisorName;

    public ClientAdvisorView(int clientId, String clientName, String email, String advisorName) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.email = email;
        this.advisorName = advisorName;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getEmail() {
        return email;
    }

    public String getAdvisorName() {
        return advisorName;
    }
}