package br.com.gestoresportivo.entity;

import jakarta.persistence.*;
import java.io.Serializable; // Necessário para a classe de ID

@Entity
@Table(name = "equipe_torneio") // Nome da tabela no banco de dados
public class EquipeTorneio implements Serializable { // Marque a entidade como Serializable também
    @EmbeddedId // Define que o ID é uma chave composta incorporada
    private EquipeTorneioId id;

    @ManyToOne // Relacionamento muitos-para-um com Equipe
    @MapsId("codEquipe") // Mapeia o campo 'codEquipe' da EquipeTorneioId para este relacionamento
    @JoinColumn(name = "cod_equipe", referencedColumnName = "cod", nullable = false)
    private Equipe equipe; // Campo que representa a entidade Equipe

    @ManyToOne // Relacionamento muitos-para-um com Torneio
    @MapsId("codTorneio") // Mapeia o campo 'codTorneio' da EquipeTorneioId para este relacionamento
    @JoinColumn(name = "cod_torneio", referencedColumnName = "cod", nullable = false)
    private Torneio torneio; // Campo que representa a entidade Torneio

    public EquipeTorneio() {}

    public EquipeTorneio(EquipeTorneioId id, Equipe equipe, Torneio torneio) {
        this.id = id;
        this.equipe = equipe;
        this.torneio = torneio;
    }

    // Getters e Setters
    public EquipeTorneioId getId() { return id; }
    public void setId(EquipeTorneioId id) { this.id = id; }
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
    public Torneio getTorneio() { return torneio; }
    public void setTorneio(Torneio torneio) { this.torneio = torneio; }
}