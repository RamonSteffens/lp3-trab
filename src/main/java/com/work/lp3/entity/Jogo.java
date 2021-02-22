package com.work.lp3.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "jogos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Aposta> apostas;

    private String timeCasa;

    private String timeFora;

    private Integer totalDeGols;

    private String placar;

    private Date dataDoJogo;

    public String getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(String timeCasa) {
        this.timeCasa = timeCasa;
    }

    public String getTimeFora() {
        return timeFora;
    }

    public void setTimeFora(String timeFora) {
        this.timeFora = timeFora;
    }

    public Integer getTotalDeGols() {
        return totalDeGols;
    }

    public void setTotalDeGols(Integer totalDeGols) {
        this.totalDeGols = totalDeGols;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Aposta> getApostas() {
        return apostas;
    }

    public void setApostas(List<Aposta> apostas) {
        this.apostas = apostas;
    }

    public void addAposta(Aposta aposta) { this.apostas.add(aposta); }

    @Override
    public String toString() {
        return id + " - Time da casa: "
                + timeCasa + " - Time visitante: "
                + timeFora + " - Placar esperado: " + placar;
    }

    public Date getDataDoJogo() {
        return dataDoJogo;
    }

    public void setDataDoJogo(Date dataDoJogo) {
        this.dataDoJogo = dataDoJogo;
    }

    public String getPlacar() {
        return placar;
    }

    public void setPlacar(String placar) {
        this.placar = placar;
    }
}
