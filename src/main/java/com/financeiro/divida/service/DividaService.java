package com.financeiro.divida.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.divida.entity.Divida;
import com.financeiro.divida.entity.Pessoa;
import com.financeiro.divida.exception.EntidadeNaoEncontradaException;
import com.financeiro.divida.repository.DividaRepository;
import com.financeiro.divida.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividaService {

  private final DividaRepository dividaRepository;
  private final PessoaRepository devedorRepository;

  @Transactional
  public Divida salvar(Divida divida, Long pessoaId) {
    Pessoa devedor = devedorRepository.findById(pessoaId)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada com id: " + pessoaId));

    divida.setPessoa(devedor);
    return dividaRepository.save(divida);
  }

  @Transactional(readOnly = true)
  public List<Divida> listarTodas() {
    return dividaRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Divida buscarPorId(Long id) {
    return dividaRepository.findById(id)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Dívida não encontrada com id: " + id));
  }

  @Transactional(readOnly = true)
  public List<Divida> listarPorDevedor(Long pessoaId) {
    if (!devedorRepository.existsById(pessoaId)) {
      throw new EntidadeNaoEncontradaException("Pessoa não encontrada com id: " + pessoaId);
    }
    return dividaRepository.findByPessoaId(pessoaId);
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
      throw new EntidadeNaoEncontradaException("Dívida não encontrada com id: " + id);
    }
    dividaRepository.deleteById(id);
  }
}
