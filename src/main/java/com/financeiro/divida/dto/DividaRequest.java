package com.financeiro.divida.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.financeiro.divida.enums.TipoDivida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DividaRequest(
  @NotBlank(message = "A descrição é obrigatória")
  String descricao,
  
  @NotNull(message = "O valor é obrigatório")
  @Positive(message = "O valor deve ser maior que zero")
  BigDecimal valor,
  
  @NotNull(message = "A data é obrigatória")
  LocalDate data,
  
  String observacao,

  @NotNull(message = "O tipo é obrigatório")
  TipoDivida tipo,
  
  @NotNull(message = "O ID da pessoa é obrigatório")
  Long pessoaId
) {}
