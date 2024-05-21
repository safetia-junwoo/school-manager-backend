package com.base.boilerplate.auth.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomInvalidOrNullException extends Exception{
    private HttpStatus errorCode;
    private String errorMessage;
}
