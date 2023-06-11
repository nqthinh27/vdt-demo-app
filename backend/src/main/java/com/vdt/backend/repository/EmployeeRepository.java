package com.vdt.backend.repository;

import com.vdt.backend.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "Select * from employee where first_name = :firstName", nativeQuery = true)
    public List<Employee> findAllByFirstName(@Param("firstName") String firstName);
}