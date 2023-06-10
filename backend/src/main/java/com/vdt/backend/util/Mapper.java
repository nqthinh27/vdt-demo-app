package com.vdt.backend.util;

import com.vdt.backend.domain.Employee;
import com.vdt.backend.domain.EmployeeDocument;

public class Mapper {
    public static EmployeeDocument convertToEmployeeDocument(Employee employee) {
        EmployeeDocument employeeDocument = new EmployeeDocument();
        employeeDocument.setId(employee.getId());
        employeeDocument.setFirstName(employee.getFirstName());
        employeeDocument.setLastName(employee.getLastName());
        employeeDocument.setAddress(employee.getAddress());
        employeeDocument.setAvatar(employee.getAvatar());
        return employeeDocument;
    }
}
