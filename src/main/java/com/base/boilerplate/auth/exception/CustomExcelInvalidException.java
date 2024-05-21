package com.base.boilerplate.auth.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomExcelInvalidException extends Exception {
    private HttpStatus errorCode;
    private List<String> errors = new ArrayList<>();

    public CustomExcelInvalidException(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
