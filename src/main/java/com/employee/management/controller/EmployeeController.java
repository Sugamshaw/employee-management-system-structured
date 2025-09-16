
package com.employee.management.controller;

import com.employee.management.dto.EmployeeDTO;
import com.employee.management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
@Validated
public class EmployeeController {

    private final EmployeeService service;
    public EmployeeController(EmployeeService service){ this.service = service; }

    @GetMapping
    public List<EmployeeDTO> all(){ return service.getAllEmployees(); }

    @GetMapping("/{id}")
    public EmployeeDTO one(@PathVariable Long id){ return service.getEmployeeById(id); }

    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@Valid @RequestBody EmployeeDTO dto){
        EmployeeDTO created = service.createEmployee(dto);
        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("data", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public Map<String,Object> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto){
        EmployeeDTO updated = service.updateEmployee(id, dto);
        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("data", updated);
        return body;
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete(@PathVariable Long id){
        service.deleteEmployee(id);
        Map<String,Object> body = new HashMap<>();
        body.put("success", true);
        body.put("message", "Deleted");
        return body;
    }

    @GetMapping("/search")
    public List<EmployeeDTO> search(@RequestParam @NotBlank String keyword){
        return service.searchEmployees(keyword);
    }

    @GetMapping("/department/{department}")
    public List<EmployeeDTO> byDept(@PathVariable String department){
        return service.getEmployeesByDepartment(department);
    }

    @GetMapping("/salary-range")
    public List<EmployeeDTO> bySalary(@RequestParam @DecimalMin("0.0") BigDecimal minSalary,
                                      @RequestParam @DecimalMin("0.0") BigDecimal maxSalary){
        return service.getEmployeesBySalaryRange(minSalary, maxSalary);
    }

    @GetMapping("/departments")
    public List<String> departments(){ return service.getAllDepartments(); }

    @GetMapping("/count/department/{department}")
    public Map<String,Object> countByDept(@PathVariable String department){
        Map<String,Object> m = new HashMap<>();
        m.put("department", department);
        m.put("count", service.getEmployeeCountByDepartment(department));
        return m;
    }

    @GetMapping("/health")
    public Map<String,Object> health(){
        Map<String,Object> m = new HashMap<>();
        m.put("status", "UP");
        m.put("service", "Employee Management System");
        return m;
    }
}
