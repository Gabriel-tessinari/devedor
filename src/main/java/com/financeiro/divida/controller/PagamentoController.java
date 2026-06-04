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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.divida.dto.PagamentoRequest;
import com.financeiro.divida.dto.PagamentoResponse;
import com.financeiro.divida.entity.Pagamento;
import com.financeiro.divida.mapper.PagamentoMapper;
import com.financeiro.divida.service.PagamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

  private final PagamentoService service;
  private final PagamentoMapper mapper;

  @PostMapping
  public ResponseEntity<PagamentoResponse> criar(@RequestBody @Valid PagamentoRequest request) {
    Pagamento entity = mapper.toEntity(request);
    Pagamento salvo = service.salvar(entity, request.dividaId());
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(salvo));
  }

  @GetMapping
  public ResponseEntity<List<PagamentoResponse>> listarTodosOuPorDividas(@RequestParam(required = false) List<Long> dividaIds) {
    if (dividaIds != null && !dividaIds.isEmpty()) {
      List<PagamentoResponse> pagamentos = service.listarPorDividas(dividaIds).stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());

      return ResponseEntity.ok(pagamentos);
    }

    List<PagamentoResponse> dtos = service.listarTodos().stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PagamentoResponse> buscarPorId(@PathVariable Long id) {
    Pagamento pagamento = service.buscarPorId(id);
    return ResponseEntity.ok(mapper.toResponse(pagamento));
  }

  @GetMapping("/divida/{dividaId}")
  public ResponseEntity<List<PagamentoResponse>> listarPorDivida(@PathVariable Long dividaId) {
    List<PagamentoResponse> dtos = service.listarPorDivida(dividaId).stream()
      .map(mapper::toResponse)
      .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PagamentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PagamentoRequest request) {
    Pagamento entity = mapper.toEntity(request);
    Pagamento atualizado = service.atualizar(id, entity);
    return ResponseEntity.ok(mapper.toResponse(atualizado));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletar(@PathVariable Long id) {
    service.deletar(id);
  }
}
