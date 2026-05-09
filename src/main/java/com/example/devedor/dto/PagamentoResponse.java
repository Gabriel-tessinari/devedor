package com.example.devedor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponse(
  Long id,
  BigDecimal valor,
  LocalDate dataPagamento,
  Long dividaId
) {}
