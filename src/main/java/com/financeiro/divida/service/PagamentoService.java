package com.financeiro.divida.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.divida.entity.Divida;
import com.financeiro.divida.entity.Pagamento;
import com.financeiro.divida.exception.EntidadeNaoEncontradaException;
import com.financeiro.divida.repository.DividaRepository;
import com.financeiro.divida.repository.PagamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {

  private final PagamentoRepository pagamentoRepository;
  private final DividaRepository dividaRepository;

  @Transactional
  public Pagamento salvar(Pagamento pagamento, Long dividaId) {
    Divida divida = dividaRepository.findById(dividaId)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Dívida não encontrada com id: " + dividaId));

    pagamento.setDivida(divida);
    return pagamentoRepository.save(pagamento);
  }

  @Transactional(readOnly = true)
  public List<Pagamento> listarTodos() {
    return pagamentoRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Pagamento buscarPorId(Long id) {
    return pagamentoRepository.findById(id)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Pagamento não encontrado com id: " + id));
  }

  @Transactional(readOnly = true)
  public List<Pagamento> listarPorDivida(Long dividaId) {
    if (!dividaRepository.existsById(dividaId)) {
        throw new EntidadeNaoEncontradaException("Dívida não encontrada com id: " + dividaId);
    }
    return pagamentoRepository.findByDividaId(dividaId);
  }

  @Transactional
  public Pagamento atualizar(Long id, Pagamento dadosAtualizados) {
    Pagamento pagamentoExistente = buscarPorId(id);
    pagamentoExistente.setValor(dadosAtualizados.getValor());
    pagamentoExistente.setData(dadosAtualizados.getData());
    return pagamentoRepository.save(pagamentoExistente);
  }

  @Transactional
  public void deletar(Long id) {
    if (!pagamentoRepository.existsById(id)) {
        throw new EntidadeNaoEncontradaException("Pagamento não encontrado com id: " + id);
    }
    pagamentoRepository.deleteById(id);
  }
}
