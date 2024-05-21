package com.base.boilerplate.auth.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomAuthException extends Exception{
    private HttpStatus errorCode;
    private String errorMessage;
}
