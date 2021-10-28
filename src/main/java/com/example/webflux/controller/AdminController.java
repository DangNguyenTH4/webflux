package com.example.webflux.controller;

import com.example.webflux.dto.AdminRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/v1/admin")
public interface AdminController {
    @PostMapping("/test")
    public ResponseEntity getAd(@Valid @RequestBody AdminRequest request);
}
