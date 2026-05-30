package com.financeiro.divida.mapper;

import org.springframework.stereotype.Component;

import com.financeiro.divida.dto.PessoaRequest;
import com.financeiro.divida.dto.PessoaResponse;
import com.financeiro.divida.entity.Pessoa;

@Component
public class PessoaMapper {

  public Pessoa toEntity(PessoaRequest request) {
    Pessoa entity = new Pessoa();
    entity.setNome(request.nome());
    return entity;
  }

  public PessoaResponse toResponse(Pessoa entity) {
    return new PessoaResponse(
      entity.getId(),
      entity.getNome()
    );
  }
}
