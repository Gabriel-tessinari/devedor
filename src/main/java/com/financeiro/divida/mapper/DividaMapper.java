package com.financeiro.divida.mapper;

import org.springframework.stereotype.Component;

import com.financeiro.divida.dto.DividaRequest;
import com.financeiro.divida.dto.DividaResponse;
import com.financeiro.divida.entity.Divida;

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
      entity.getPessoa().getId()
    );
  }
}
