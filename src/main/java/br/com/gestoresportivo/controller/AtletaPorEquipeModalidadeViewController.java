package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.AtletaPorEquipeModalidadeResponseDTO; // Importe o DTO
import br.com.gestoresportivo.service.AtletaPorEquipeModalidadeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class AtletaPorEquipeModalidadeViewController {

    @Autowired
    private AtletaPorEquipeModalidadeViewService viewService;

    @GetMapping("/atletas-por-equipe-modalidade")
    public ResponseEntity<List<AtletaPorEquipeModalidadeResponseDTO>> getAtletasPorEquipeModalidade() {
        List<AtletaPorEquipeModalidadeResponseDTO> results = viewService.buscarTodosAtletasPorEquipeModalidade();

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(results);
    }
}