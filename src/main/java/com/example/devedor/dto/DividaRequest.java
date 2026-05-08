package com.example.devedor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
  
  @NotNull(message = "O ID do devedor é obrigatório")
  Long devedorId
) {}
