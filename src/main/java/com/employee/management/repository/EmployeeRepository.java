
package com.employee.management.repository;

import com.employee.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);
    List<Employee> findByDepartmentIgnoreCase(String department);
    List<Employee> findBySalaryBetween(BigDecimal minSalary, BigDecimal maxSalary);

    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> search(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT e.department FROM Employee e ORDER BY e.department")
    List<String> findAllDepartments();

    boolean existsByEmailIgnoreCase(String email);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department = :department")
    Long countByDepartment(@Param("department") String department);

    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE LOWER(e.email) = LOWER(:email) AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}
