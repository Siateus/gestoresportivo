package br.com.gestoresportivo.service.dto;

import java.time.LocalDate;


public class AtletaDTO {

    private Integer cod;

    private String cpf;
    private String nome;
    private Character sexo;
    private String cidade;
    private LocalDate dataNasc;


    public AtletaDTO() {
    }

    public AtletaDTO(Integer cod, String cpf, String nome, Character sexo, String cidade, LocalDate dataNasc) {
        this.cod = cod;
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.cidade = cidade;
        this.dataNasc = dataNasc;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return "AtletaDTO{" +
                "cod=" + cod +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", sexo=" + sexo +
                ", cidade='" + cidade + '\'' +
                ", dataNasc=" + dataNasc +
                '}';
    }


}