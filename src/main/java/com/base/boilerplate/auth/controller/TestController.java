package com.base.boilerplate.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/test")
public class TestController {

    @GetMapping("")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body(null);
    }
}
