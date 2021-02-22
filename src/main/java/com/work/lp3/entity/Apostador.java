package com.work.lp3.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Apostador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String apelido;

    private String email;

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


    public void setApelido(String nome) {
        this.apelido = nome;
    }

    public String getApelido() {
        return apelido;
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
        return id + " - Nome: " + nome + " -- Apelido: " + apelido + " -- Data de aniversario: " + dataAniversario + " -- Email: " + email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
