package com.vdt.backend.controller;

import com.vdt.backend.service.FakerDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faker_data")
public class FakerDataController {
    private final FakerDataService fakerDataService;

    public FakerDataController(FakerDataService fakerDataService) {
        this.fakerDataService = fakerDataService;
    }

    @GetMapping("/{count}")
    public String generateFakeDataList(@PathVariable int count) {
        return fakerDataService.generateFakeDataList(count);
    }
}