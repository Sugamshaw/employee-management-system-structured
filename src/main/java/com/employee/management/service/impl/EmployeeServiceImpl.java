
package com.employee.management.service.impl;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.entity.Employee;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo){ this.repo = repo; }

    private EmployeeDTO toDTO(Employee e){
        return new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(),
                e.getDepartment(), e.getSalary(), e.getHireDate());
    }

    private Employee toEntity(EmployeeDTO d){
        Employee e = new Employee();
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setEmail(d.getEmail());
        e.setDepartment(d.getDepartment());
        e.setSalary(d.getSalary());
        e.setHireDate(d.getHireDate());
        return e;
    }

    @Override @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        Employee e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return toDTO(e);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        if (repo.existsByEmailIgnoreCase(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        Employee saved = repo.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        if (repo.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setDepartment(dto.getDepartment());
        e.setSalary(dto.getSalary());
        e.setHireDate(dto.getHireDate());
        return toDTO(repo.save(e));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Employee not found: " + id);
        repo.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public List<EmployeeDTO> searchEmployees(String keyword) {
        return repo.search(keyword).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        return repo.findByDepartmentIgnoreCase(department).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesBySalaryRange(BigDecimal min, BigDecimal max) {
        return repo.findBySalaryBetween(min, max).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<String> getAllDepartments() { return repo.findAllDepartments(); }

    @Override @Transactional(readOnly = true)
    public Long getEmployeeCountByDepartment(String department) { return repo.countByDepartment(department); }
}
