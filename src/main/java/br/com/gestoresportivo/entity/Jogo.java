package br.com.gestoresportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "jogo")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-gerado pelo banco
    @Column(name = "cod") // Mapeia o campo 'id' para a coluna 'cod' no DB
    private Integer id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "p_time1", nullable = false) // Mapeia para snake_case e não nulo
    private Integer pTime1;

    @Column(name = "p_time2", nullable = false) // Mapeia para snake_case e não nulo
    private Integer pTime2;

    @Column(nullable = false, length = 10)
    private String placar;

    @ManyToOne // Relacionamento com Equipe 1
    @JoinColumn(name = "cod_equipe1", referencedColumnName = "cod", nullable = false)
    // ^^^ 'name' é a coluna FK na tabela 'jogo'
    // ^^^ 'referencedColumnName' é a PK na tabela 'equipe'
    private Equipe equipe1; // Mapeia para a entidade Equipe

    @ManyToOne // Relacionamento com Equipe 2
    @JoinColumn(name = "cod_equipe2", referencedColumnName = "cod", nullable = false)
    private Equipe equipe2; // Mapeia para a entidade Equipe

    @ManyToOne // Relacionamento com Torneio
    @JoinColumn(name = "cod_torneio", referencedColumnName = "cod", nullable = false)
    private Torneio torneio; // Mapeia para a entidade Torneio

    public Jogo() {}

    // Construtor para criar novos jogos (sem ID, que é gerado)
    public Jogo(LocalDate data, LocalTime hora, Integer pTime1, Integer pTime2, String placar,
                Equipe equipe1, Equipe equipe2, Torneio torneio) {
        this.data = data;
        this.hora = hora;
        this.pTime1 = pTime1;
        this.pTime2 = pTime2;
        this.placar = placar;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.torneio = torneio;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public Integer getpTime1() { return pTime1; }
    public void setpTime1(Integer pTime1) { this.pTime1 = pTime1; }
    public Integer getpTime2() { return pTime2; }
    public void setpTime2(Integer pTime2) { this.pTime2 = pTime2; }
    public String getPlacar() { return placar; }
    public void setPlacar(String placar) { this.placar = placar; }
    public Equipe getEquipe1() { return equipe1; } // Retorna a entidade Equipe
    public void setEquipe1(Equipe equipe1) { this.equipe1 = equipe1; } // Recebe a entidade Equipe
    public Equipe getEquipe2() { return equipe2; } // Retorna a entidade Equipe
    public void setEquipe2(Equipe equipe2) { this.equipe2 = equipe2; } // Recebe a entidade Equipe
    public Torneio getTorneio() { return torneio; } // Retorna a entidade Torneio
    public void setTorneio(Torneio torneio) { this.torneio = torneio; } // Recebe a entidade Torneio
}