package com.example.devedor.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

  public EntidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
}
