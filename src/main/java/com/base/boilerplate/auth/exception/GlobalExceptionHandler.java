package com.base.boilerplate.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomFileException.class})
    public ResponseEntity<CustomFileException> errorHandle(CustomFileException customFileException){
        return ResponseEntity.status(customFileException.getErrorCode()).body(customFileException);
    }

    @ExceptionHandler(value = {CustomInvalidOrNullException.class})
    public ResponseEntity<CustomInvalidOrNullException> errorHandle(CustomInvalidOrNullException customInvalidorNullException){
        return ResponseEntity.status(customInvalidorNullException.getErrorCode()).body(customInvalidorNullException);
    }
    @ExceptionHandler(value = {CustomExcelInvalidException.class})
    public ResponseEntity<List<String>> erroorHandle(CustomExcelInvalidException customExcelInvalidException){
        return ResponseEntity.status(customExcelInvalidException.getErrorCode()).body(customExcelInvalidException.getErrors());
    }

    @ExceptionHandler(value = {CustomAuthException.class})
    public ResponseEntity<CustomAuthException> errorHandle(CustomAuthException customAuthException){
        return ResponseEntity.status(customAuthException.getErrorCode()).body(customAuthException);
    }

//    @ExceptionHandler(value = {MenuException.class})
//    public ResponseEntity<MenuException> errorHandle(MenuException menuException){
//        return ResponseEntity.status(menuException.getErrorCode()).body(menuException);
//    }

    @ExceptionHandler(value = {CustomTokenException.class})
    public ResponseEntity<CustomTokenException> errorHandle(CustomTokenException customTokenException) {
        return ResponseEntity.status(customTokenException.getErrorCode()).body(customTokenException);
    }

//    @ExceptionHandler(value = {MedicationException.class})
//    public ResponseEntity<MedicationException> errorHandle(MedicationException medicationException){
//        return ResponseEntity.status(medicationException.getErrorCode()).body(medicationException);
//    }
}
