package com.example.devedor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PagamentoRequest(
  @NotNull(message = "O valor é obrigatório")
  @Positive(message = "O valor deve ser maior que zero")
  BigDecimal valor,

  @NotNull(message = "A data é obrigatória")
  LocalDate data,

  @NotNull(message = "O ID da dívida é obrigatório")
  Long dividaId
) {}
