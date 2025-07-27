package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipe")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer codModalidade;
    private String nome;
    private String sigla;
    public Equipe() {}
    public Equipe(Integer id, Integer codModalidade, String nome, String sigla) {
        this.id = id;
        this.codModalidade = codModalidade;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodModalidade() {
        return codModalidade;
    }

    public void setCodModalidade(Integer codModalidade) {
        this.codModalidade = codModalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
