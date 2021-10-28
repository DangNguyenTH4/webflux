package com.example.webflux.controller;

import com.example.webflux.dto.AdminRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdminControllerImpl implements AdminController{

    @Override
    public ResponseEntity getAd(AdminRequest request) {
        log.info(request.getName());
        return ResponseEntity.ok(request.getName());
    }
}
