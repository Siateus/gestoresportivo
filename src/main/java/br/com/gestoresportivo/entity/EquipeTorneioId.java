package br.com.gestoresportivo.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EquipeTorneioId implements Serializable {
    private Integer codEquipe;
    private Integer codTorneio;

    public EquipeTorneioId() {}

    public EquipeTorneioId(Integer codEquipe, Integer codTorneio) {
        this.codEquipe = codEquipe;
        this.codTorneio = codTorneio;
    }

    public Integer getCodEquipe() {
        return codEquipe;
    }

    public void setCodEquipe(Integer codEquipe) {
        this.codEquipe = codEquipe;
    }

    public Integer getCodTorneio() {
        return codTorneio;
    }

    public void setCodTorneio(Integer codTorneio) {
        this.codTorneio = codTorneio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EquipeTorneioId that = (EquipeTorneioId) o;
        return Objects.equals(codEquipe, that.codEquipe) && Objects.equals(codTorneio, that.codTorneio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codEquipe, codTorneio);
    }
}
