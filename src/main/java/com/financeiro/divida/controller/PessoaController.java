package com.financeiro.divida.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.divida.dto.PessoaRequest;
import com.financeiro.divida.dto.PessoaResponse;
import com.financeiro.divida.service.PessoaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

  private final PessoaService service;

  @GetMapping
  public ResponseEntity<List<PessoaResponse>> listar() {
    return ResponseEntity.ok(service.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaResponse> buscar(@PathVariable Long id) {
    return service.buscarPorId(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<PessoaResponse> criar(@RequestBody PessoaRequest devedor) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(devedor));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaResponse> atualizar(@PathVariable Long id, @RequestBody PessoaRequest devedor) {
    return ResponseEntity.ok(service.atualizar(id, devedor));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
