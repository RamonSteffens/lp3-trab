package com.work.lp3.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
public class Aposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double valorAposta;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Jogo> jogos;

    @ManyToOne
    private Apostador apostador;

    private Date dataDeCriacaoAposta = Date.from(Instant.now());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(Double valorAposta) {
        this.valorAposta = valorAposta;
    }

    public Apostador getApostador() {
        return apostador;
    }

    public void setApostador(Apostador apostadorId) {
        this.apostador = apostadorId;
    }

    @Override
    public String toString() {
        return id + " - Valor apostado: "
                + valorAposta + " - Apostador: " + getApostador().getNome()
                + " - Jogos: " + jogos;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
    }

    public Date getDataDeCriacaoAposta() {
        return dataDeCriacaoAposta;
    }

    public void setDataDeCriacaoAposta(Date dataDeCriacaoAposta) {
        this.dataDeCriacaoAposta = dataDeCriacaoAposta;
    }
}
