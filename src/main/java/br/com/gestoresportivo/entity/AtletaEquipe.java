package br.com.gestoresportivo.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;

@Entity
@Table(name = "atletaequipe")
public class AtletaEquipe {
    @EmbeddedId
    private EquipeTorneioId id;

    @ManyToOne
    @JoinColumn(name = "codEquipe", referencedColumnName = "id", insertable = false, updatable = false)
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "codAtleta", referencedColumnName = "id", insertable = false, updatable = false)
    private Atleta atleta;

    public AtletaEquipe() {}

    public AtletaEquipe(EquipeTorneioId id, Equipe equipe, Atleta atleta) {
        this.id = id;
        this.equipe = equipe;
        this.atleta = atleta;
    }

    public EquipeTorneioId getId() {
        return id;
    }

    public void setId(EquipeTorneioId id) {
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
