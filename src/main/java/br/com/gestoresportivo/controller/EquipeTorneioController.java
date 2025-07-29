package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.EquipeTorneioDTO;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.EquipeTorneio;
import br.com.gestoresportivo.entity.EquipeTorneioId;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.service.EquipeTorneioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipes-torneios")
public class EquipeTorneioController {

    @Autowired
    private EquipeTorneioService equipeTorneioService;

    // POST /equipes-torneios - Associar uma equipe a um torneio
    @PostMapping
    public ResponseEntity<EquipeTorneio> criarEquipeTorneio(@RequestBody @Valid EquipeTorneioDTO equipeTorneioDTO) {

        EquipeTorneioId id = new EquipeTorneioId(equipeTorneioDTO.getCodEquipe(), equipeTorneioDTO.getCodTorneio());

        Equipe equipe = new Equipe();
        equipe.setId(equipeTorneioDTO.getCodEquipe());

        Torneio torneio = new Torneio();
        torneio.setId(equipeTorneioDTO.getCodTorneio());

        EquipeTorneio equipeTorneio = new EquipeTorneio(id, equipe, torneio);


        EquipeTorneio associacaoSalva = equipeTorneioService.salvarEquipeTorneio(equipeTorneio);
        return ResponseEntity.status(HttpStatus.CREATED).body(associacaoSalva);
    }

    // GET /equipes-torneios - Listar todas as associações
    @GetMapping
    public ResponseEntity<List<EquipeTorneio>> listarTodasEquipesTorneios() {
        List<EquipeTorneio> associacoes = equipeTorneioService.buscarTodasEquipesTorneios();
        if (associacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(associacoes);
    }

    // GET /equipes-torneios/{codEquipe}/{codTorneio} - Buscar associação por chave composta
    @GetMapping("/{codEquipe}/{codTorneio}")
    public ResponseEntity<EquipeTorneio> buscarEquipeTorneioPorId(
            @PathVariable Integer codEquipe,
            @PathVariable Integer codTorneio) {

        EquipeTorneioId id = new EquipeTorneioId(codEquipe, codTorneio);
        Optional<EquipeTorneio> associacao = equipeTorneioService.buscarEquipeTorneioPorId(id);

        if (associacao.isPresent()) {
            return ResponseEntity.ok(associacao.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /equipes-torneios/{codEquipe}/{codTorneio} - Excluir uma associação
    @DeleteMapping("/{codEquipe}/{codTorneio}")
    public ResponseEntity<Void> deletarEquipeTorneio(
            @PathVariable Integer codEquipe,
            @PathVariable Integer codTorneio) {

        EquipeTorneioId id = new EquipeTorneioId(codEquipe, codTorneio);
        boolean deletado = equipeTorneioService.deletarEquipeTorneio(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}