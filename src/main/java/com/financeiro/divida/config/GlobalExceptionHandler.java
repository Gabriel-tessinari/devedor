package com.financeiro.divida.config;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.financeiro.divida.exception.EntidadeNaoEncontradaException;
import com.financeiro.divida.exception.NegocioException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(MethodArgumentNotValidException ex) {
      String mensagens = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagens, LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> tratarErroLeituraJson(HttpMessageNotReadableException ex) {
      String mensagem = "Erro na formatação do JSON ou valor inválido enviado.";
      Throwable causaRaiz = ex.getCause();

      if (causaRaiz != null && causaRaiz.getClass().getSimpleName().equals("InvalidFormatException")) {
        try {
          String valoresAceitos = "valores válidos";
          java.lang.reflect.Method getTargetType = causaRaiz.getClass().getMethod("getTargetType");
          Class<?> targetType = (Class<?>) getTargetType.invoke(causaRaiz);
          
          if (targetType != null && targetType.isEnum()) {
            valoresAceitos = java.util.Arrays.toString(targetType.getEnumConstants());
          }
          
          String nomeCampo = "enviado";
          String detalheErro = causaRaiz.getMessage();
          if (detalheErro != null && detalheErro.contains("[\"")) {
            int inicio = detalheErro.lastIndexOf("[\"") + 2;
            int fim = detalheErro.lastIndexOf("\"]");
            if (inicio > 1 && fim > inicio) {
              nomeCampo = detalheErro.substring(inicio, fim);
            }
          }
            
          mensagem = String.format("O valor enviado para o campo '%s' é inválido. Valores aceitos: %s.", nomeCampo, valoresAceitos);
            
        } catch (Exception e) {
          mensagem = "Valor inválido enviado para um dos campos do JSON.";
        }
      }

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, LocalDateTime.now()));
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
