package com.example.devedor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.devedor.dto.DevedorRequest;
import com.example.devedor.dto.DevedorResponse;
import com.example.devedor.entity.Devedor;
import com.example.devedor.exception.EntidadeNaoEncontradaException;
import com.example.devedor.exception.NegocioException;
import com.example.devedor.repository.DevedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevedorService {

  private final DevedorRepository repository;

  public List<DevedorResponse> listar() {
    return repository.findAll().stream()
      .map(d -> new DevedorResponse(d.getId(), d.getNome()))
      .toList();
  }

  public Optional<DevedorResponse> buscarPorId(Long id) {
    return repository.findById(id)
      .map(d -> new DevedorResponse(d.getId(), d.getNome()));
  }

  public DevedorResponse salvar(DevedorRequest request) {
    if (repository.existsByNome(request.getNome())) {
      throw new NegocioException("Já existe um devedor com o nome: " + request.getNome());
    }

    Devedor devedor = new Devedor();
    devedor.setNome(request.getNome());
    
    devedor = repository.save(devedor);
    return new DevedorResponse(devedor.getId(), devedor.getNome());
  }

  public DevedorResponse atualizar(Long id, DevedorRequest request) {
    Devedor devedor = repository.findById(id)
      .map(d -> {
        d.setNome(request.getNome());
        return repository.save(d);
      })
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Devedor não encontrado com id: " + id));

    return new DevedorResponse(devedor.getId(), devedor.getNome());
  }

  public void deletar(Long id) {
    if (!repository.existsById(id)) {
      throw new EntidadeNaoEncontradaException("Não é possível deletar: Devedor não encontrado.");
    }
    repository.deleteById(id);
  }
}
