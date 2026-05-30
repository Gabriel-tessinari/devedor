package com.financeiro.divida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.divida.entity.Divida;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {

    List<Divida> findByPessoaId(Long pessoaId);
}
