package br.com.gestoresportivo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipe_torneio")
public class EquipeTorneio {
    @EmbeddedId
    private EquipeTorneioId id;

    @ManyToOne
    @MapsId("codEquipe")
    @JoinColumn(name = "cod_equipe", referencedColumnName = "cod")
    private Equipe equipe;

    @ManyToOne
    @MapsId("codTorneio")
    @JoinColumn(name = "cod_torneio", referencedColumnName = "cod")
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
