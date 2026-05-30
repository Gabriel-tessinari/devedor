package com.financeiro.devedor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.devedor.entity.Devedor;
import com.financeiro.devedor.entity.Divida;
import com.financeiro.devedor.exception.EntidadeNaoEncontradaException;
import com.financeiro.devedor.repository.DevedorRepository;
import com.financeiro.devedor.repository.DividaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividaService {

  private final DividaRepository dividaRepository;
  private final DevedorRepository devedorRepository;

  @Transactional
  public Divida salvar(Divida divida, Long devedorId) {
    Devedor devedor = devedorRepository.findById(devedorId)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Devedor não encontrado com id: " + devedorId));

    divida.setDevedor(devedor);
    return dividaRepository.save(divida);
  }

  @Transactional(readOnly = true)
  public List<Divida> listarTodas() {
    return dividaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Divida buscarPorId(Long id) {
    return dividaRepository.findById(id)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Divida não encontrada com id: " + id));
  }

  @Transactional(readOnly = true)
  public List<Divida> listarPorDevedor(Long devedorId) {
    if (!devedorRepository.existsById(devedorId)) {
      throw new EntidadeNaoEncontradaException("Devedor não encontrado com id: " + devedorId);
    }
    return dividaRepository.findByDevedorId(devedorId);
  }

  @Transactional
  public Divida atualizar(Long id, Divida dadosAtualizados) {
    Divida dividaExistente = buscarPorId(id);
    dividaExistente.setDescricao(dadosAtualizados.getDescricao());
    dividaExistente.setValor(dadosAtualizados.getValor());
    dividaExistente.setData(dadosAtualizados.getData());
    dividaExistente.setObservacao(dadosAtualizados.getObservacao());
    return dividaRepository.save(dividaExistente);
  }

  @Transactional
  public void deletar(Long id) {
    if (!dividaRepository.existsById(id)) {
      throw new EntidadeNaoEncontradaException("Divida não encontrada com id: " + id);
    }
    dividaRepository.deleteById(id);
  }
}
