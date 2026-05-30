package com.financeiro.devedor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.devedor.entity.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByDividaId(Long dividaId);
}
