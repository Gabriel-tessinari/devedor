package com.financeiro.divida.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DividaResponse(
  Long id,
  String descricao,
  BigDecimal valor,
  LocalDate data,
  String observacao,
  Long devedorId
) {}
