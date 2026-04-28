package com.example.devedor.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.devedor.exception.EntidadeNaoEncontradaException;
import com.example.devedor.exception.NegocioException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResposta> tratarNaoEncontrado(EntidadeNaoEncontradaException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErroResposta(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErroResposta> tratarNegocio(NegocioException ex) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
        .body(new ErroResposta(HttpStatus.UNPROCESSABLE_CONTENT.value(), ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroInesperado(Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErroResposta(500, "Ocorreu um erro interno inesperado.", LocalDateTime.now()));
    }

    @Getter
    @AllArgsConstructor
    public static class ErroResposta {
        private int status;
        private String mensagem;
        private LocalDateTime timestamp;
    }
}
