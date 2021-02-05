package com.work.lp3.repository;

import com.work.lp3.entity.Aposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApostaRepository extends JpaRepository<Aposta, Integer> {

    Optional<List<Aposta>> findAllByApostadorId(Integer apostadorId);
}
