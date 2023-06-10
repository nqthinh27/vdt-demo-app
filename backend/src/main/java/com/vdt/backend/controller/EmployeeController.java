package com.vdt.backend.controller;

import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;
import com.vdt.backend.domain.dto.Response;
import com.vdt.backend.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Response> getAllEmps(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        Page<Employee> emps = employeeService.getAllEmps(pageable);
        if (emps.getContent().size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("Employee data is not available!"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("Get Employee data successfully!", emps.getTotalElements(), emps.getTotalPages(), emps.getNumber(), emps.getContent()));
    }
    @GetMapping("/unpageable/")
    public List<Employee> getAllEmps() {
        return employeeService.getAllEmpsUnpageable();
    }

    @PostMapping("/")
    public ResponseEntity createNewEmp(@RequestBody Employee newEmp) {
        String msg = employeeService.createEmpWithoutAvatar(newEmp);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/sync/")
    public String syncEmployees(){
        return employeeService.syncEmployees(employeeService.getAllEmpsUnpageable());
    }


    @GetMapping("/search/address/{keyword}/")
    public List<EmployeeDocument> finByAddress(@PathVariable String keyword) {
        return employeeService.findByAddress(keyword);
    }

    @GetMapping("/getAllEmpsEs/")
    public Iterable<EmployeeDocument> getAllEmpEs() {
        return employeeService.getAllEmpEs();
    }

    @GetMapping("/fuzzysearch/{address}/")
    public List<EmployeeDocument> findByAddressFuzzy(@PathVariable String address) {
        return employeeService.findByAddressFuzzy(address);
    }
}
