package com.financeiro.devedor.mapper;

import org.springframework.stereotype.Component;

import com.financeiro.devedor.dto.DividaRequest;
import com.financeiro.devedor.dto.DividaResponse;
import com.financeiro.devedor.entity.Divida;

@Component
public class DividaMapper {

  public Divida toEntity(DividaRequest request) {
    Divida entity = new Divida();
    entity.setDescricao(request.descricao());
    entity.setValor(request.valor());
    entity.setData(request.data());
    entity.setObservacao(request.observacao());
    return entity;
  }

  public DividaResponse toResponse(Divida entity) {
    return new DividaResponse(
      entity.getId(),
      entity.getDescricao(),
      entity.getValor(),
      entity.getData(),
      entity.getObservacao(),
      entity.getDevedor().getId()
    );
  }
}
