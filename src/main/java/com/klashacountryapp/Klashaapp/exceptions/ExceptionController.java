package com.klashacountryapp.Klashaapp.exceptions;

import com.klashacountryapp.Klashaapp.exceptions.responseDto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequest(BadRequestException ex) {
        ExceptionResponseDto er = ExceptionResponseDto.builder()
                .message(ex.getMessage())
                .date(LocalDate.now())
                .error(true)
                .build();
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error(e.getMessage(), e);
        ExceptionResponseDto er = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .date(LocalDate.now())
                .error(true)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }
}
