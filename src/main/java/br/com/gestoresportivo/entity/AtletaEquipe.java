package br.com.gestoresportivo.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "atleta_equipe")
public class AtletaEquipe implements Serializable {

    @EmbeddedId // Define que o ID é uma chave composta incorporada
    private AtletaEquipeId id;

    @ManyToOne
    @MapsId("codEquipe") // Mapeia o campo 'codEquipe' do AtletaEquipeId para este relacionamento
    @JoinColumn(name = "cod_equipe", referencedColumnName = "cod", nullable = false)
    private Equipe equipe; // Campo que representa a entidade Equipe

    @ManyToOne // Relacionamento muitos-para-um com Atleta
    @MapsId("codAtleta") // Mapeia o campo 'codAtleta' do AtletaEquipeId para este relacionamento
    @JoinColumn(name = "cod_atleta", referencedColumnName = "cod", nullable = false)
    private Atleta atleta;
    public AtletaEquipe() {}

    public AtletaEquipe(AtletaEquipeId id, Equipe equipe, Atleta atleta) {
        this.id = id;
        this.equipe = equipe;
        this.atleta = atleta;
    }

    // Getters e Setters
    public AtletaEquipeId getId() { return id; }
    public void setId(AtletaEquipeId id) { this.id = id; }
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }
}