package br.com.gestoresportivo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modalidade")
public class Modalidade {
    @Id
    private Integer id;
    private String nome;
    private Integer qtdAtlentaEquipe;

    public Modalidade() {}

    public Modalidade(Integer id, String nome, Integer qtdAtlentaEquipe) {
        this.id = id;
        this.nome = nome;
        this.qtdAtlentaEquipe = qtdAtlentaEquipe;
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

    public Integer getQtdAtlentaEquipe() {
        return qtdAtlentaEquipe;
    }

    public void setQtdAtlentaEquipe(Integer qtdAtlentaEquipe) {
        this.qtdAtlentaEquipe = qtdAtlentaEquipe;
    }
}
