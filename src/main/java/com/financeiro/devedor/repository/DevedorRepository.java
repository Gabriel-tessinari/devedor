package com.financeiro.devedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.devedor.entity.Devedor;

@Repository
public interface DevedorRepository extends JpaRepository<Devedor, Long> {
  
  boolean existsByNome(String nome);
}
