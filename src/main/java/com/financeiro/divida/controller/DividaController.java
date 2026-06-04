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

import com.financeiro.divida.dto.DividaRequest;
import com.financeiro.divida.dto.DividaResponse;
import com.financeiro.divida.entity.Divida;
import com.financeiro.divida.mapper.DividaMapper;
import com.financeiro.divida.service.DividaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dividas")
@RequiredArgsConstructor
public class DividaController {

  private final DividaService service;
  private final DividaMapper mapper;

  @PostMapping
  public ResponseEntity<DividaResponse> criar(@RequestBody @Valid DividaRequest request) {
    Divida entity = mapper.toEntity(request);
    Divida salva = service.salvar(entity, request.pessoaId());
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(salva));
  }

  @GetMapping
  public ResponseEntity<List<DividaResponse>> listarTodas() {
    List<DividaResponse> dtos = service.listarTodas().stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());
      
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DividaResponse> buscarPorId(@PathVariable Long id) {
    Divida divida = service.buscarPorId(id);
    return ResponseEntity.ok(mapper.toResponse(divida));
  }

  @GetMapping("/pessoa/{pessoaId}")
  public ResponseEntity<List<DividaResponse>> listarPorPessoa(@PathVariable Long pessoaId) {
    List<DividaResponse> dtos = service.listarPorPessoa(pessoaId).stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DividaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid DividaRequest request) {
    Divida entity = mapper.toEntity(request);
    Divida atualizada = service.atualizar(id, entity);
    return ResponseEntity.ok(mapper.toResponse(atualizada));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    service.deletar(id);
  }
}
