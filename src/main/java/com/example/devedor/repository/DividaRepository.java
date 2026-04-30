package com.example.devedor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devedor.entity.Divida;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {

    List<Divida> findByDevedorId(Long devedorId);
}
