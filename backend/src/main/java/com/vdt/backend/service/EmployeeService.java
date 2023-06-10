package com.vdt.backend.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;
import com.vdt.backend.repository.EmployeeRepository;
import com.vdt.backend.repository.EmployeeRepositoryElasticsearch;
import com.vdt.backend.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepositoryElasticsearch employeeRepositoryElasticsearch;
    private final EmployeeRepository employeeRepository;
    private final MinIOService minIOService;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRepositoryElasticsearch employeeRepositoryElasticsearch, MinIOService minIOService) {
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryElasticsearch = employeeRepositoryElasticsearch;
        this.minIOService = minIOService;
    }


    public String syncEmployees() {
        String message = "Error synchronize data";
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDocument> employeeDocuments = employees.stream()
                .map(Mapper::convertToEmployeeDocument)
                .collect(Collectors.toList());
        try {
            employeeRepositoryElasticsearch.saveAll(employeeDocuments);
            message = "Synchronized data Mysql and Elasticsearch";
            log.info(message);
        } catch (Exception e) {
            log.error(message);
        }
        return message;
    }

    public Page<Employee> getAllEmps(Pageable pageable) {
        Page<Employee> emps = employeeRepository.findAll(pageable);
        log.info("Get all employees");
        return new PageImpl<>(emps.getContent(), pageable, emps.getTotalElements());
    }

    public String createEmpWithoutAvatar(Employee employee) {
        String message = "Create failed!";
        List<String> avatars = minIOService.getAllImageUrls();
        Random random = new Random();
        int randomNumber = random.nextInt(avatars.size());
        employee.setAvatar(avatars.get(randomNumber));
        try {
            employeeRepository.save(employee);
            employeeRepositoryElasticsearch.save(Mapper.convertToEmployeeDocument(employee));
            message = "Add an employee successfully!";
            log.info(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return message;
    }

    public List<Employee> getAllEmpsUnpageable() {
        List<Employee> emps = employeeRepository.findAll();
        log.info("Get all employees");
        return emps;
    }

    public List<EmployeeDocument> findByAddress(String keyword) {
        return employeeRepositoryElasticsearch.findByAddress(keyword);
    }

    public Iterable<EmployeeDocument> getAllEmpEs() {
        return employeeRepositoryElasticsearch.findAll();
    }

    public List<EmployeeDocument> findByAddressFuzzy(String address) {
        return employeeRepositoryElasticsearch.findByAddressFuzzy(address);
    }
}
