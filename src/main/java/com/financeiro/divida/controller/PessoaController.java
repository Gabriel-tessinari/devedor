package com.financeiro.divida.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.divida.dto.PessoaRequest;
import com.financeiro.divida.dto.PessoaResponse;
import com.financeiro.divida.entity.Pessoa;
import com.financeiro.divida.mapper.PessoaMapper;
import com.financeiro.divida.service.PessoaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

  private final PessoaService service;
  private final PessoaMapper mapper;

  @PostMapping
  public ResponseEntity<PessoaResponse> criar(@RequestBody PessoaRequest request) {
    Pessoa entity = mapper.toEntity(request);
    Pessoa salva = service.salvar(entity);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(salva));
  }

  @GetMapping
  public ResponseEntity<List<PessoaResponse>> listarTodas() {
    List<PessoaResponse> dtos = service.listarTodas().stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());
      
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaResponse> buscar(@PathVariable Long id) {
    Pessoa pessoa = service.buscarPorId(id);
    return ResponseEntity.ok(mapper.toResponse(pessoa));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaResponse> atualizar(@PathVariable Long id, @RequestBody PessoaRequest request) {
    Pessoa entity = mapper.toEntity(request);
    Pessoa atualizada = service.atualizar(id, entity);
    return ResponseEntity.ok(mapper.toResponse(atualizada));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    service.deletar(id);
  }
}
