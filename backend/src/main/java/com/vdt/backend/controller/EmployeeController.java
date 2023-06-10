package com.vdt.backend.controller;

import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;
import com.vdt.backend.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> getAllEmps() {
        return employeeService.getAllEmps();
    }

    @PostMapping("/sync/")
    public String syncEmployees(){
        return employeeService.syncEmployees();
    }


    @GetMapping("/search/address/{keyword}")
    public List<EmployeeDocument> finByAddress(@PathVariable String keyword) {
        return employeeService.findByAddress(keyword);
    }

    @GetMapping("/matchAllProducts/")
    public Iterable<EmployeeDocument> getAllEmpEs() {
        return employeeService.getAllEmpEs();
    }
}
