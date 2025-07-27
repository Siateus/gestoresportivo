package br.com.gestoresportivo.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class AtletaEquipeId implements Serializable {
    private Integer codAtleta;
    private Integer codEquipe;

    public AtletaEquipeId() {}
    public AtletaEquipeId(Integer codAtleta, Integer codEquipe) {
        this.codAtleta = codAtleta;
        this.codEquipe = codEquipe;
    }

    public Integer getCodAtleta() {return codAtleta;}
    public void setCodAtleta(Integer codAtleta) {this.codAtleta = codAtleta;}
    public Integer getCodEquipe() {return codEquipe;}
    public void setCodEquipe(Integer codEquipe) {this.codEquipe = codEquipe;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AtletaEquipeId that = (AtletaEquipeId) o;
        return Objects.equals(codAtleta, that.codAtleta) && Objects.equals(codEquipe, that.codEquipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codAtleta, codEquipe);
    }
}
