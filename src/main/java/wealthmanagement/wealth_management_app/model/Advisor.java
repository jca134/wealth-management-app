package wealthmanagement.wealth_management_app.model;

import java.time.LocalDate;

public class Advisor {
    private Integer advisorId;
    private String name;
    private String email;
    private String phone;
    private String officeLocation;
    private LocalDate hireDate;

    public Advisor() {
    }

    public Advisor(Integer advisorId, String name, String email, String phone,
                   String officeLocation, LocalDate hireDate) {
        this.advisorId = advisorId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.officeLocation = officeLocation;
        this.hireDate = hireDate;
    }

    public Integer getAdvisorId() { return advisorId; }
    public void setAdvisorId(Integer advisorId) { this.advisorId = advisorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}
