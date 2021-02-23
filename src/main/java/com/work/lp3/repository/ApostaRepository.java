package com.work.lp3.repository;

import com.work.lp3.entity.Aposta;
import com.work.lp3.entity.Apostador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApostaRepository extends JpaRepository<Aposta, Integer> {

    Optional<List<Aposta>> findAllByApostadorId(Integer apostadorId);

    Integer countAllByApostadorId(Integer apostadorId);
}
