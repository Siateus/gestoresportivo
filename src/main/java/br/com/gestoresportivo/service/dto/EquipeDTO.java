package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EquipeDTO {

    @NotBlank(message = "O nome da equipe não pode estar em branco")
    @Size(max = 30, message = "O nome deve ter no máximo 30 caracteres")
    private String nome;

    @NotBlank(message = "A sigla da equipe não pode estar em branco")
    @Size(min = 3, max = 5, message = "A sigla deve ter entre 3 e 5 caracteres")
    private String sigla;

    @NotNull(message = "O código da modalidade não pode ser nulo")
    private Integer codModalidade;

    public EquipeDTO() {}

    public EquipeDTO(String nome, String sigla, Integer codModalidade) {
        this.nome = nome;
        this.sigla = sigla;
        this.codModalidade = codModalidade;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getCodModalidade() {
        return codModalidade;
    }

    public void setCodModalidade(Integer codModalidade) {
        this.codModalidade = codModalidade;
    }
}