package br.com.gestoresportivo.service.dto;

import br.com.gestoresportivo.validation.CpfValido; // <--- Importe sua nova anotação

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class AtletaDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos") // Garante o tamanho após o Pattern
    @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter apenas 11 dígitos numéricos") // Garante apenas números
    @CpfValido(message = "CPF fornecido é inválido.") // <--- ADICIONE ESTA ANOTAÇÃO AQUI!
    private String cpf;

    @NotNull(message = "O sexo não pode ser nulo")
    @Pattern(regexp = "[fmFM]", message = "O sexo deve ser 'f' ou 'm'")
    @Size(min = 1, max = 1, message = "O sexo deve conter apenas um caractere")
    private String sexo; // Alterado para String para compatibilidade com @Pattern

    @NotBlank(message = "A cidade não pode estar em branco")
    private String cidade;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    // ... (construtores, getters e setters)
    public AtletaDTO() {}

    public AtletaDTO(String nome, String cpf, String sexo, String cidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.cidade = cidade;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
}