package com.financeiro.divida.mapper;

import org.springframework.stereotype.Component;

import com.financeiro.divida.dto.PagamentoRequest;
import com.financeiro.divida.dto.PagamentoResponse;
import com.financeiro.divida.entity.Pagamento;

@Component
public class PagamentoMapper {

  public Pagamento toEntity(PagamentoRequest request) {
    Pagamento entity = new Pagamento();
    entity.setValor(request.valor());
    entity.setData(request.data());
    return entity;
  }

  public PagamentoResponse toResponse(Pagamento entity) {
    return new PagamentoResponse(
      entity.getId(),
      entity.getValor(),
      entity.getData(),
      entity.getDivida().getId()
    );
  }
}
