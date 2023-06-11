package com.vdt.backend.controller;

import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;
import com.vdt.backend.domain.dto.Response;
import com.vdt.backend.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private CacheManager cacheManager;
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
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

    /**
     * Normal search
     * @param keyword
     * @return
     */
    @GetMapping("/search/address/{keyword}/")
    public List<EmployeeDocument> finByAddress(@PathVariable String keyword) {
        return employeeService.findByAddress(keyword);
    }

    /**
     * Get all from Elasticsearch
     * @return
     */
    @GetMapping("/getAllEmpsEs/")
    public Iterable<EmployeeDocument> getAllEmpEs() {
        return employeeService.getAllEmpEs();
    }

    /**
     * Fuzzy search
     * @param address
     * @return
     */
    @GetMapping("/fuzzysearch/address/{address}/")
    public List<EmployeeDocument> findByAddressFuzzy(@PathVariable String address) {
        return employeeService.findByAddressFuzzy(address);
    }

    /**
     * Get Employee by id
     * @param id
     * @return
     */
    @GetMapping("/id/{id}/")
    public Employee getEmpById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    /**
     * Get Employee by firstName
     * @param firstName
     * @return
     */
    @GetMapping("/firstName/{firstName}/nocache/")
    public List<Employee> getEmployeesByFirstNameWithoutCache(@PathVariable String firstName) {
        return employeeService.getEmpsByFirstName(firstName);
    }
    @Cacheable(value = "employee", key = "#firstName")
    @GetMapping("/firstName/{firstName}/")
    public List<Employee> getEmployeesByFirstName(@PathVariable String firstName) {
        if (!cacheHit(firstName)) {
            log.warn("Cache miss for Employee with firstName: " + firstName);
        }
        return employeeService.getEmpsByFirstName(firstName);
    }

    /**
     * Check cache
     * @param name
     * @return
     */
    private boolean cacheHit(String name) {
        Cache cache = cacheManager.getCache("employee");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(name);
            return valueWrapper != null;
        }
        return false;
    }
}
