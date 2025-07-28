package br.com.gestoresportivo.service.dto;

public class AtletaPorEquipeModalidadeResponseDTO {
    private String nomeAtleta;
    private String nomeEquipe;
    private String nomeModalidade;

    public AtletaPorEquipeModalidadeResponseDTO(String nomeAtleta, String nomeEquipe, String nomeModalidade) {
        this.nomeAtleta = nomeAtleta;
        this.nomeEquipe = nomeEquipe;
        this.nomeModalidade = nomeModalidade;
    }

    // Getters e Setters
    public String getNomeAtleta() { return nomeAtleta; }
    public void setNomeAtleta(String nomeAtleta) { this.nomeAtleta = nomeAtleta; }
    public String getNomeEquipe() { return nomeEquipe; }
    public void setNomeEquipe(String nomeEquipe) { this.nomeEquipe = nomeEquipe; }
    public String getNomeModalidade() { return nomeModalidade; }
    public void setNomeModalidade(String nomeModalidade) { this.nomeModalidade = nomeModalidade; }
}