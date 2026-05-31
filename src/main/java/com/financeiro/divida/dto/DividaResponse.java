package com.financeiro.divida.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.financeiro.divida.enums.TipoDivida;

public record DividaResponse(
  Long id,
  String descricao,
  BigDecimal valor,
  LocalDate data,
  String observacao,
  TipoDivida tipo,
  Long pessoaId
) {}
