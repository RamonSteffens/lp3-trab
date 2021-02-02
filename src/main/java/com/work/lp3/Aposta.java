package com.work.lp3;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Aposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date dataCriacao;

    private Double valorAposta;

    @ManyToMany
    private List<Jogo> jogos;

    @ManyToOne
    private Apostador apostador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(Double valorAposta) {
        this.valorAposta = valorAposta;
    }

    public Apostador getApostadorId() {
        return apostador;
    }

    public void setApostadorId(Apostador apostadorId) {
        this.apostador = apostadorId;
    }

    @Override
    public String toString() {
        return "Aposta{" +
                "id=" + id +
                ", dataCriacao=" + dataCriacao +
                ", valorAposta=" + valorAposta +
                ", apostadorId=" + apostador +
                '}';
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}