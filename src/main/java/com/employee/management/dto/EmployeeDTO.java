
package com.employee.management.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDTO {
    private Long id;

    @NotBlank @Size(max = 50)
    private String firstName;

    @NotBlank @Size(max = 50)
    private String lastName;

    @NotBlank @Email @Size(max = 100)
    private String email;

    @NotBlank @Size(max = 50)
    private String department;

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal salary;

    @NotNull
    private LocalDate hireDate;

    public EmployeeDTO(){}

    public EmployeeDTO(Long id, String firstName, String lastName, String email,
                       String department, BigDecimal salary, LocalDate hireDate) {
        this.id = id; this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.department = department; this.salary = salary; this.hireDate = hireDate;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}
