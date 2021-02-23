package com.work.lp3.repository;

import com.work.lp3.entity.Aposta;
import com.work.lp3.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    Optional<Jogo> findAllByDataDoJogo(Date dataDoJogo);

    Optional<List<Jogo>> findAllByApostasIn(Collection<List<Aposta>> apostas);


}
