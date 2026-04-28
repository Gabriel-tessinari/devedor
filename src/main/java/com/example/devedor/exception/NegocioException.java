package com.example.devedor.exception;

public class NegocioException extends RuntimeException {

  public NegocioException(String mensagem) {
    super(mensagem);
  }
}
