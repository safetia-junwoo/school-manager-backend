package com.base.boilerplate.auth.controller;

import com.base.boilerplate.api.sample.dto.SampleRequestDTO;
import com.base.boilerplate.auth.dto.UserRequestDTO;
import com.base.boilerplate.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @GetMapping("")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok().body(null);
    }

    @Operation(description = "유저 저장")
    @PostMapping("")
    public ResponseEntity<?> createItem(@RequestBody UserRequestDTO requestDto) {
        Integer id = userService.upsertItem(requestDto);
        return ResponseEntity.ok().body(id);
    }

    @Operation(description = "유저 수정")
    @PutMapping("")
    public ResponseEntity<?> updateItem(@RequestBody UserRequestDTO requestDto) throws Exception{
        Integer id = userService.upsertItem(requestDto);
        return ResponseEntity.ok().body(id);
    }

}
