package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.NotNull;

public class AtletaEquipeDTO {

    @NotNull(message = "O código do atleta não pode ser nulo")
    private Integer codAtleta;

    @NotNull(message = "O código da equipe não pode ser nulo")
    private Integer codEquipe;

    public AtletaEquipeDTO() {}

    public AtletaEquipeDTO(Integer codAtleta, Integer codEquipe) {
        this.codAtleta = codAtleta;
        this.codEquipe = codEquipe;
    }

    // Getters e Setters
    public Integer getCodAtleta() { return codAtleta; }
    public void setCodAtleta(Integer codAtleta) { this.codAtleta = codAtleta; }
    public Integer getCodEquipe() { return codEquipe; }
    public void setCodEquipe(Integer codEquipe) { this.codEquipe = codEquipe; }
}