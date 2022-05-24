package com.runbook.backend.testing.rest;

import com.runbook.backend.testing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private ProductService productService;

    @GetMapping("/get")
    public void getProductByName() {
        productService.getProductByName("television");
    }
}
