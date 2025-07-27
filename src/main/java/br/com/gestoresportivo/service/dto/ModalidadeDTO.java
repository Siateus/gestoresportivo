package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ModalidadeDTO {

    @NotBlank(message = "O nome da modalidade não pode estar em branco")
    @Size(max = 30, message = "O nome deve ter no máximo 30 caracteres")
    private String nome;

    @NotNull(message = "A quantidade de atletas por equipe não pode ser nula")
    @Positive(message = "A quantidade de atletas por equipe deve ser um número positivo")
    private Integer qtdAtletaEquipe;

    public ModalidadeDTO() {}

    public ModalidadeDTO(String nome, Integer qtdAtletaEquipe) {
        this.nome = nome;
        this.qtdAtletaEquipe = qtdAtletaEquipe;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdAtletaEquipe() {
        return qtdAtletaEquipe;
    }

    public void setQtdAtletaEquipe(Integer qtdAtletaEquipe) {
        this.qtdAtletaEquipe = qtdAtletaEquipe;
    }
}