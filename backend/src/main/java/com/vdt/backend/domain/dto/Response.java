package com.vdt.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private List<T> data;

    public Response(String message){
        this.message = message;
        this.totalElements = 0;
        this.totalPages = 0;
        this.currentPage = 0;
        this.data = null;
    }
}
