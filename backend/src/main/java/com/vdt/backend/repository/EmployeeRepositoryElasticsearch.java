package com.vdt.backend.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import com.vdt.backend.domain.EmployeeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeRepositoryElasticsearch extends ElasticsearchRepository<EmployeeDocument, Long> {
    @Query("{\"bool\": {\"must\": {\"match\": {\"address\": \"?0\"}}}}")
    List<EmployeeDocument> findByAddress(String fieldValue);

    @Query("{\"fuzzy\": {\"address\": {\"value\": \"?0\", \"fuzziness\": \"auto\"}}}")
    List<EmployeeDocument> findByAddressFuzzy(String address);
}
