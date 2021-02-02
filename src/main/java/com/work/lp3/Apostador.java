package com.work.lp3;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Apostador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Date dataAniversario;

    //POSSUI UMA APOSTA
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apostador")
    private List<Aposta> aposta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Aposta> getAposta() {
        return aposta;
    }

    public void setAposta(List<Aposta> aposta) {
        this.aposta = aposta;
    }

    public Date getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(Date dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " -- Data de aniversario:" + dataAniversario;
    }
}
