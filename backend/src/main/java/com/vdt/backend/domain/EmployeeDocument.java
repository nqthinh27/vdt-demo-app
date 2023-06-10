package com.vdt.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "employee")
public class EmployeeDocument {
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String avatar;
}
