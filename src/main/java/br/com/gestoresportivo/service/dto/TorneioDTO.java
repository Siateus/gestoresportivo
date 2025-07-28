package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TorneioDTO {

    @NotBlank(message = "O nome do torneio não pode estar em branco")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotNull(message = "A quantidade de equipes não pode ser nula")
    @Positive(message = "A quantidade de equipes deve ser um número positivo")
    private Integer qtdEquipe;

    @NotNull(message = "O código da modalidade não pode ser nulo")
    private Integer codModalidade; // Para criar/atualizar, recebemos apenas o ID da modalidade

    public TorneioDTO() {}

    public TorneioDTO(String nome, Integer qtdEquipe, Integer codModalidade) {
        this.nome = nome;
        this.qtdEquipe = qtdEquipe;
        this.codModalidade = codModalidade;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdEquipe() {
        return qtdEquipe;
    }

    public void setQtdEquipe(Integer qtdEquipe) {
        this.qtdEquipe = qtdEquipe;
    }

    public Integer getCodModalidade() {
        return codModalidade;
    }

    public void setCodModalidade(Integer codModalidade) {
        this.codModalidade = codModalidade;
    }
}