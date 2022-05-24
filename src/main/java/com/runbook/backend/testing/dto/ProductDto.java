package com.runbook.backend.testing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer productId;

    private String date;

    private String name;

    public void functionThrowsException() throws Exception {
        throw new IOException();
    }

}
