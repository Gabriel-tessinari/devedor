package com.example.devedor.mapper;

import org.springframework.stereotype.Component;

import com.example.devedor.dto.PagamentoRequest;
import com.example.devedor.dto.PagamentoResponse;
import com.example.devedor.entity.Pagamento;

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
