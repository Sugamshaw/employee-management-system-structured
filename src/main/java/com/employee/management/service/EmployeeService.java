
package com.employee.management.service;

import com.employee.management.dto.EmployeeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createEmployee(EmployeeDTO dto);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);
    void deleteEmployee(Long id);
    List<EmployeeDTO> searchEmployees(String keyword);
    List<EmployeeDTO> getEmployeesByDepartment(String department);
    List<EmployeeDTO> getEmployeesBySalaryRange(BigDecimal min, BigDecimal max);
    List<String> getAllDepartments();
    Long getEmployeeCountByDepartment(String department);
}
