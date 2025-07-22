package br.com.gestoresportivo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "torneio")
public class Torneio {
    @Id
    private Integer id;
    private String nome;
    private Integer qtdEquipe;
    private Integer codModalidade;

    public Torneio() {}
    public Torneio(Integer id, String nome, Integer qtdEquipe, Integer codModalidade) {
        this.id = id;
        this.nome = nome;
        this.qtdEquipe = qtdEquipe;
        this.codModalidade = codModalidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdEquipe() {
        return qtdEquipe;
    }

    public void setQtdEquipe(Integer qtdEquipe) {
        this.qtdEquipe = qtdEquipe;
    }

    public Integer getCodModalidade() {
        return codModalidade;
    }

    public void setCodModalidade(Integer codModalidade) {
        this.codModalidade = codModalidade;
    }
}
