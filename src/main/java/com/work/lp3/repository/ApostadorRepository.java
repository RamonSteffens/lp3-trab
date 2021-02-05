package com.work.lp3.repository;

import com.work.lp3.entity.Apostador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApostadorRepository extends JpaRepository<Apostador, Integer> {

    Optional<Apostador> findByNome(String nome);
}
