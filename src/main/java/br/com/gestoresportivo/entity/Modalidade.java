package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modalidade") // Tabela no banco de dados
public class Modalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private Integer id;

    @Column(nullable = false, length = 30)
    private String nome;

    @Column(name = "qtd_atleta_equipe", nullable = false)
    private Integer qtdAtletaEquipe;

    public Modalidade() {}

    public Modalidade(String nome, Integer qtdAtletaEquipe) {
        this.nome = nome;
        this.qtdAtletaEquipe = qtdAtletaEquipe;
    }

    // Getters e Setters
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

    public Integer getQtdAtletaEquipe() {
        return qtdAtletaEquipe;
    }

    public void setQtdAtletaEquipe(Integer qtdAtletaEquipe) {
        this.qtdAtletaEquipe = qtdAtletaEquipe;
    }
}