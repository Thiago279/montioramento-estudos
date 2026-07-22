package com.toma.monitor_estudos.exception;

import com.toma.monitor_estudos.dto.erro.ErroResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SessaoInvalidaException.class)
    public ResponseEntity<ErroResponse> handleSessaoInvalida(SessaoInvalidaException ex, HttpServletRequest request){
        ErroResponse erroResponse = buildErroResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        ErroResponse erroResponse = buildErroResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){

        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(";\n "));
        ErroResponse erroResponse = buildErroResponse(HttpStatus.BAD_REQUEST,mensagem, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    private ErroResponse buildErroResponse(HttpStatus status, String mensagem, HttpServletRequest request) {
        return  new ErroResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                request.getRequestURI()
        );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleErrosNaoTratados(Exception ex, HttpServletRequest request) {

        ErroResponse erroResponse = buildErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no servidor. Por favor, tente novamente mais tarde.",
                request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroResponse);
    }

}
