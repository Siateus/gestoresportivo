package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.NotNull;

public class EquipeTorneioDTO {

    @NotNull(message = "O código da equipe não pode ser nulo")
    private Integer codEquipe;

    @NotNull(message = "O código do torneio não pode ser nulo")
    private Integer codTorneio;

    public EquipeTorneioDTO() {}

    public EquipeTorneioDTO(Integer codEquipe, Integer codTorneio) {
        this.codEquipe = codEquipe;
        this.codTorneio = codTorneio;
    }

    // Getters e Setters
    public Integer getCodEquipe() { return codEquipe; }
    public void setCodEquipe(Integer codEquipe) { this.codEquipe = codEquipe; }
    public Integer getCodTorneio() { return codTorneio; }
    public void setCodTorneio(Integer codTorneio) { this.codTorneio = codTorneio; }
}