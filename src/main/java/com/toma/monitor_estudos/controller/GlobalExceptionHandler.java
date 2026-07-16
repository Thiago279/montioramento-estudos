package com.toma.monitor_estudos.controller;

import com.toma.monitor_estudos.exception.ErroResponse;
import com.toma.monitor_estudos.exception.SessaoInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SessaoInvalidaException.class)
    public ResponseEntity<ErroResponse> handleSessaoInvalida(SessaoInvalidaException ex, HttpServletRequest request){
        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleErrosNaoTratados(Exception ex, HttpServletRequest request) {
        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro interno inesperado no servidor. Por favor, tente novamente mais tarde.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroResponse);
    }

}
