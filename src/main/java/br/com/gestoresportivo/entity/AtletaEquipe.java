package br.com.gestoresportivo.entity;

import jakarta.persistence.*;
import java.io.Serializable; // Importar se EquipeTorneioId ainda estiver neste arquivo, caso contrário remover

@Entity
@Table(name = "atleta_equipe") // Ajustado para snake_case no nome da tabela também
public class AtletaEquipe {
    @EmbeddedId
    private AtletaEquipeId id; // Usando AtletaEquipeId como discutido

    @ManyToOne
    @MapsId("codEquipe") // Mapeia codEquipe do AtletaEquipeId para este relacionamento
    @JoinColumn(name = "cod_equipe", referencedColumnName = "cod") // <--- CORREÇÃO AQUI!
    private Equipe equipe;

    @ManyToOne
    @MapsId("codAtleta") // Mapeia codAtleta do AtletaEquipeId para este relacionamento
    @JoinColumn(name = "cod_atleta", referencedColumnName = "cod") // <--- CORREÇÃO AQUI!
    private Atleta atleta;

    // Construtor padrão obrigatório
    public AtletaEquipe() {}

    // Construtor para conveniência
    public AtletaEquipe(AtletaEquipeId id, Equipe equipe, Atleta atleta) {
        this.id = id;
        this.equipe = equipe;
        this.atleta = atleta;
    }

    // Getters e Setters
    public AtletaEquipeId getId() {
        return id;
    }

    public void setId(AtletaEquipeId id) {
        this.id = id;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }
}