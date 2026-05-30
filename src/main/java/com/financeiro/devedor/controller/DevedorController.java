package com.financeiro.devedor.controller;

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

import com.financeiro.devedor.dto.DevedorRequest;
import com.financeiro.devedor.dto.DevedorResponse;
import com.financeiro.devedor.service.DevedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/devedores")
@RequiredArgsConstructor
public class DevedorController {

  private final DevedorService service;

  @GetMapping
  public ResponseEntity<List<DevedorResponse>> listar() {
    return ResponseEntity.ok(service.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<DevedorResponse> buscar(@PathVariable Long id) {
    return service.buscarPorId(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<DevedorResponse> criar(@RequestBody DevedorRequest devedor) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(devedor));
  }

  @PutMapping("/{id}")
  public ResponseEntity<DevedorResponse> atualizar(@PathVariable Long id, @RequestBody DevedorRequest devedor) {
    return ResponseEntity.ok(service.atualizar(id, devedor));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
