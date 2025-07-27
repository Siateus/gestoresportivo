package br.com.gestoresportivo.service.dto; // Exemplo de pacote para DTOs

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank; // Para validação
import jakarta.validation.constraints.NotNull; // Para validação
import jakarta.validation.constraints.Pattern; // Para validação de formato
import jakarta.validation.constraints.Past; // Para validação de data

public class AtletaDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos")
    // Considere também uma validação de CPF mais robusta, se necessário
    private String cpf;

    @NotNull(message = "O sexo não pode ser nulo")
    @Pattern(regexp = "[fmFM]", message = "O sexo deve ser 'f' ou 'm'")
    private Character sexo;

    @NotBlank(message = "A cidade não pode estar em branco")
    private String cidade;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    public AtletaDTO() {}

    public AtletaDTO(String nome, String cpf, Character sexo, String cidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.cidade = cidade;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}