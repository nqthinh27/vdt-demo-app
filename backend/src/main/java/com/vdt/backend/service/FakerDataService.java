package com.vdt.backend.service;

import com.github.javafaker.Faker;
import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.dto.EmployeeFaker;
import com.vdt.backend.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FakerDataService {
    private final EmployeeService employeeService;

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final MinIOService minIOService;

    public FakerDataService(EmployeeRepository employeeRepository, MinIOService minIOService, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.minIOService = minIOService;
        this.employeeService = employeeService;
    }

    public String generateFakeDataList(int count) {
        String message = "Add failed!";
        List<Employee> dataList = new ArrayList<>();
        Faker faker = new Faker();
        List<String> avatars = minIOService.getAllImageUrls();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Employee data = new Employee();
            data.setFirstName(faker.name().firstName());
            data.setLastName(faker.name().lastName());
            data.setAddress(faker.address().fullAddress());
            int randomNumber = random.nextInt(avatars.size());
            data.setAvatar(avatars.get(randomNumber));
            dataList.add(data);
        }
        try {
            List<Employee> emps = employeeRepository.saveAll(dataList);
            message = "Add " + emps.size() + " employees successfully!";
            log.info(message);
            String syncMgs = employeeService.syncEmployees(emps);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return message;
    }
}
