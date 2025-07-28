package br.com.gestoresportivo.service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

public class JogoDTO {

    @NotNull(message = "A data do jogo não pode ser nula")
    @FutureOrPresent(message = "A data do jogo não pode ser no passado") // Ou @PastOrPresent se jogos passados forem aceitos
    private LocalDate data;

    @NotNull(message = "O horário do jogo não pode ser nulo")
    private LocalTime hora;

    @NotNull(message = "A pontuação da primeira equipe não pode ser nula")
    @Min(value = 0, message = "A pontuação da primeira equipe deve ser maior ou igual a zero")
    private Integer pTime1; // Nome do campo em camelCase

    @NotNull(message = "A pontuação da segunda equipe não pode ser nula")
    @Min(value = 0, message = "A pontuação da segunda equipe deve ser maior ou igual a zero")
    private Integer pTime2; // Nome do campo em camelCase

    @NotBlank(message = "O placar não pode estar em branco")
    @Pattern(regexp = "^\\d+X\\d+$", message = "O placar deve estar no formato 'pontosXpontos' (ex: 3X1)") // Validação para "numXnum"
    private String placar;

    @NotNull(message = "O código da primeira equipe não pode ser nulo")
    private Integer codEquipe1; // Recebe o ID da Equipe 1

    @NotNull(message = "O código da segunda equipe não pode ser nulo")
    private Integer codEquipe2; // Recebe o ID da Equipe 2

    @NotNull(message = "O código do torneio não pode ser nulo")
    private Integer codTorneio; // Recebe o ID do Torneio

    public JogoDTO() {}

    // Construtor
    public JogoDTO(LocalDate data, LocalTime hora, Integer pTime1, Integer pTime2, String placar,
                   Integer codEquipe1, Integer codEquipe2, Integer codTorneio) {
        this.data = data;
        this.hora = hora;
        this.pTime1 = pTime1;
        this.pTime2 = pTime2;
        this.placar = placar;
        this.codEquipe1 = codEquipe1;
        this.codEquipe2 = codEquipe2;
        this.codTorneio = codTorneio;
    }

    // Getters e Setters
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public Integer getpTime1() { return pTime1; }
    public void setpTime1(Integer pTime1) { this.pTime1 = pTime1; }
    public Integer getpTime2() { return pTime2; }
    public void setpTime2(Integer pTime2) { this.pTime2 = pTime2; }
    public String getPlacar() { return placar; }
    public void setPlacar(String placar) { this.placar = placar; }
    public Integer getCodEquipe1() { return codEquipe1; }
    public void setCodEquipe1(Integer codEquipe1) { this.codEquipe1 = codEquipe1; }
    public Integer getCodEquipe2() { return codEquipe2; }
    public void setCodEquipe2(Integer codEquipe2) { this.codEquipe2 = codEquipe2; }
    public Integer getCodTorneio() { return codTorneio; }
    public void setCodTorneio(Integer codTorneio) { this.codTorneio = codTorneio; }
}