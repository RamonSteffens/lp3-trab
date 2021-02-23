package com.work.lp3.repository;

import com.work.lp3.entity.Apostador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApostadorRepository extends JpaRepository<Apostador, Integer> {

    Optional<Apostador> findByNome(String nome);

    @Query("SELECT a FROM Apostador a where a.apelido = :apelido AND a.nome = :nome")
    Optional<Apostador> findByApelidoAndNome(@Param("apelido") String apelido,
                                                     @Param("nome") String nome);
}
