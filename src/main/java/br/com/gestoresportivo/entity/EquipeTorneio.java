package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipetorneio")
public class EquipeTorneio {
    @EmbeddedId
    private EquipeTorneioId id;

    @ManyToOne
    @JoinColumn(name = "codEquipe", referencedColumnName = "id", insertable = false, updatable = false)
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name = "codTorneio", referencedColumnName = "id", insertable = false, updatable = false)
    private Torneio torneio;

    public EquipeTorneio() {}

    public EquipeTorneio(EquipeTorneioId id, Equipe equipe, Torneio torneio) {
        this.id = id;
        this.equipe = equipe;
        this.torneio = torneio;
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

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }
}
