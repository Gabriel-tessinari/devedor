package com.financeiro.divida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.divida.entity.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

  List<Pagamento> findAllByOrderByDataAsc();
  List<Pagamento> findByDividaIdOrderByDataAsc(Long dividaId);
  List<Pagamento> findByDividaIdInOrderByDataAsc(List<Long> dividaIds);
}
