package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipe")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private Integer id;

    @Column(nullable = false, length = 30)
    private String nome;

    @Column(nullable = false, length = 5)
    private String sigla;

    @ManyToOne
    @JoinColumn(name = "cod_modalidade", referencedColumnName = "cod", nullable = false)
    private Modalidade modalidade;

    public Equipe() {}

    public Equipe(String nome, String sigla, Modalidade modalidade) {
        this.nome = nome;
        this.sigla = sigla;
        this.modalidade = modalidade;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public Modalidade getModalidade() {
        return modalidade;
    }
    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}