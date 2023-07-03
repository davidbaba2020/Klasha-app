package com.klashacountryapp.Klashaapp.exceptions.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseDto {
    private boolean error;
    private String message;
    private LocalDate date;
    protected HttpStatus status;


    public ExceptionResponseDto(ExceptionResponseDto er, HttpStatus httpStatus) {
    }
}
