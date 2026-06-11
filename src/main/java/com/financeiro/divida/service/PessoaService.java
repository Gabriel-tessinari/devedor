package com.financeiro.divida.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.divida.entity.Pessoa;
import com.financeiro.divida.exception.EntidadeNaoEncontradaException;
import com.financeiro.divida.exception.NegocioException;
import com.financeiro.divida.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository repository;

  @Transactional
  public Pessoa salvar(Pessoa pessoa) {
    if (repository.existsByNome(pessoa.getNome())) {
      throw new NegocioException("Já existe uma pessoa com o nome: " + pessoa.getNome());
    }
    return repository.save(pessoa);
  }

  @Transactional(readOnly = true)
  public List<Pessoa> listarTodas() {
    return repository.findAllByOrderByNomeAsc();
  }

  @Transactional(readOnly = true)
  public Pessoa buscarPorId(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa não encontrada com id: " + id));
  }

  @Transactional
  public Pessoa atualizar(Long id, Pessoa dadosAtualizados) {
    Pessoa pessoaExistente = buscarPorId(id);
    pessoaExistente.setNome(dadosAtualizados.getNome());
    return repository.save(pessoaExistente);
  }

  @Transactional
  public void deletar(Long id) {
    if (!repository.existsById(id)) {
      throw new EntidadeNaoEncontradaException("Pessoa não encontrada com id: " + id);
    }
    repository.deleteById(id);
  }
}
