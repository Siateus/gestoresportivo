package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "torneio") // Nome da tabela no banco de dados
public class Torneio {
    @Id // Define 'id' como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-gerado pelo banco
    @Column(name = "cod") // Mapeia o campo 'id' para a coluna 'cod' no DB
    private Integer id;

    @Column(nullable = false, length = 30) // Nome do torneio, não nulo, máx 30 caracteres
    private String nome;

    @Column(name = "qtd_equipe", nullable = false) // Quantidade de equipes por torneio, mapeado para snake_case
    private Integer qtdEquipe;

    @ManyToOne // Relacionamento muitos para um: muitos torneios podem ter uma modalidade
    @JoinColumn(name = "cod_modalidade", referencedColumnName = "cod", nullable = false)
    // ^^^ 'name' é a coluna FK na tabela 'torneio'
    // ^^^ 'referencedColumnName' é a PK na tabela 'modalidade'
    // ^^^ 'nullable = false' indica que um torneio SEMPRE deve ter uma modalidade
    private Modalidade modalidade; // Campo que representa a entidade Modalidade

    public Torneio() {}

    // Construtor para criar novos torneios (sem ID, que é gerado, e recebendo a entidade Modalidade)
    public Torneio(String nome, Integer qtdEquipe, Modalidade modalidade) {
        this.nome = nome;
        this.qtdEquipe = qtdEquipe;
        this.modalidade = modalidade;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getQtdEquipe() { return qtdEquipe; }
    public void setQtdEquipe(Integer qtdEquipe) { this.qtdEquipe = qtdEquipe; }

    public Modalidade getModalidade() {
        return modalidade;
    }
    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}