package com.financeiro.divida.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.financeiro.divida.dto.PessoaRequest;
import com.financeiro.divida.dto.PessoaResponse;
import com.financeiro.divida.entity.Pessoa;
import com.financeiro.divida.exception.EntidadeNaoEncontradaException;
import com.financeiro.divida.exception.NegocioException;
import com.financeiro.divida.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository repository;

  public List<PessoaResponse> listar() {
    return repository.findAll().stream()
      .map(d -> new PessoaResponse(d.getId(), d.getNome()))
      .toList();
  }

  public Optional<PessoaResponse> buscarPorId(Long id) {
    return repository.findById(id)
      .map(d -> new PessoaResponse(d.getId(), d.getNome()));
  }

  public PessoaResponse salvar(PessoaRequest request) {
    if (repository.existsByNome(request.getNome())) {
      throw new NegocioException("Já existe um devedor com o nome: " + request.getNome());
    }

    Pessoa devedor = new Pessoa();
    devedor.setNome(request.getNome());
    
    devedor = repository.save(devedor);
    return new PessoaResponse(devedor.getId(), devedor.getNome());
  }

  public PessoaResponse atualizar(Long id, PessoaRequest request) {
    Pessoa devedor = repository.findById(id)
      .map(d -> {
        d.setNome(request.getNome());
        return repository.save(d);
      })
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Devedor não encontrado com id: " + id));

    return new PessoaResponse(devedor.getId(), devedor.getNome());
  }

  public void deletar(Long id) {
    if (!repository.existsById(id)) {
      throw new EntidadeNaoEncontradaException("Não é possível deletar: Devedor não encontrado.");
    }
    repository.deleteById(id);
  }
}
