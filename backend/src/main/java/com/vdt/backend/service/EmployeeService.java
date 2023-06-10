package com.vdt.backend.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;
import com.vdt.backend.repository.EmployeeRepository;
import com.vdt.backend.repository.EmployeeRepositoryElasticsearch;
import com.vdt.backend.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepositoryElasticsearch employeeRepositoryElasticsearch;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRepositoryElasticsearch employeeRepositoryElasticsearch) {
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryElasticsearch = employeeRepositoryElasticsearch;
    }



    public String syncEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDocument> employeeDocuments = employees.stream()
                .map(Mapper::convertToEmployeeDocument)
                .collect(Collectors.toList());
        employeeRepositoryElasticsearch.saveAll(employeeDocuments);
        return "Success";
    }

    public List<Employee> getAllEmps() {
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
}
