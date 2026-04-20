package wealthmanagement.wealth_management_app.model;

import java.time.LocalDate;

public class Client {
    private int clientId;
    private int advisorId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate dob;

    public Client() {
    }

    public Client(int clientId, int advisorId, String name, String email, String phone, String address, LocalDate dob) {
        this.clientId = clientId;
        this.advisorId = advisorId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}