package br.com.gestoresportivo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "jogo")
public class Jogo {
    @Id
    private Integer id;
    private LocalDate data;
    private LocalTime hora;
    private Integer pTime1;
    private Integer pTime2;
    private String placar;
    private Integer codEquipe1;
    private Integer codEquipe2;
    private Integer codTorneio;

    public Jogo() {}
    public Jogo(Integer id, LocalDate data, LocalTime hora, Integer pTime1, Integer pTime2, String placar,
                Integer codEquipe1, Integer codEquipe2, Integer codTorneio) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.pTime1 = pTime1;
        this.pTime2 = pTime2;
        this.placar = placar;
        this.codEquipe1 = codEquipe1;
        this.codEquipe2 = codEquipe2;
        this.codTorneio = codTorneio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getpTime1() {
        return pTime1;
    }

    public void setpTime1(Integer pTime1) {
        this.pTime1 = pTime1;
    }

    public Integer getpTime2() {
        return pTime2;
    }

    public void setpTime2(Integer pTime2) {
        this.pTime2 = pTime2;
    }

    public String getPlacar() {
        return placar;
    }

    public void setPlacar(String placar) {
        this.placar = placar;
    }

    public Integer getCodEquipe1() {
        return codEquipe1;
    }

    public void setCodEquipe1(Integer codEquipe1) {
        this.codEquipe1 = codEquipe1;
    }

    public Integer getCodEquipe2() {
        return codEquipe2;
    }

    public void setCodEquipe2(Integer codEquipe2) {
        this.codEquipe2 = codEquipe2;
    }

    public Integer getCodTorneio() {
        return codTorneio;
    }

    public void setCodTorneio(Integer codTorneio) {
        this.codTorneio = codTorneio;
    }
}
