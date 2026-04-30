package com.example.devedor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.devedor.entity.Devedor;
import com.example.devedor.entity.Divida;
import com.example.devedor.exception.EntidadeNaoEncontradaException;
import com.example.devedor.repository.DevedorRepository;
import com.example.devedor.repository.DividaRepository;

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
  public List<Divida> listarPorDevedor(Long devedorId) {
    if (!devedorRepository.existsById(devedorId)) {
      throw new EntidadeNaoEncontradaException("Devedor não encontrado com id: " + devedorId);
    }
    return dividaRepository.findByDevedorId(devedorId);
  }

  @Transactional(readOnly = true)
  public Divida buscarPorId(Long id) {
    return dividaRepository.findById(id)
      .orElseThrow(() -> new EntidadeNaoEncontradaException("Divida não encontrada com id: " + id));
  }

  @Transactional
  public void deletar(Long id) {
    if (!dividaRepository.existsById(id)) {
      throw new EntidadeNaoEncontradaException("Divida não encontrada com id: " + id);
    }
    dividaRepository.deleteById(id);
  }
}
