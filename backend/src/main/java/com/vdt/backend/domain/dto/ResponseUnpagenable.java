package com.vdt.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUnpagenable {
    private String message;
    private Object data;

    public ResponseUnpagenable(String message){
        this.message = message;
        this.data = null;
    }
}
