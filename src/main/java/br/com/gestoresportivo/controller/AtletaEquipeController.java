package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.AtletaEquipeDTO;
import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.entity.AtletaEquipe;
import br.com.gestoresportivo.entity.AtletaEquipeId;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.service.AtletaEquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atletas-equipes")
public class AtletaEquipeController {

    @Autowired
    private AtletaEquipeService atletaEquipeService;

    // POST /atletas-equipes - Associar um atleta a uma equipe
    @PostMapping
    public ResponseEntity<AtletaEquipe> criarAtletaEquipe(@RequestBody @Valid AtletaEquipeDTO atletaEquipeDTO) {
        // Cria a chave composta
        AtletaEquipeId id = new AtletaEquipeId(atletaEquipeDTO.getCodAtleta(), atletaEquipeDTO.getCodEquipe());

        // Cria instâncias de Atleta e Equipe apenas com o ID, para o Service buscar a entidade completa
        Atleta atleta = new Atleta();
        atleta.setId(atletaEquipeDTO.getCodAtleta());

        Equipe equipe = new Equipe();
        equipe.setId(atletaEquipeDTO.getCodEquipe());

        AtletaEquipe atletaEquipe = new AtletaEquipe(id, equipe, atleta);
        AtletaEquipe associacaoSalva = atletaEquipeService.salvarAtletaEquipe(atletaEquipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(associacaoSalva);
    }

    // GET /atletas-equipes - Listar todas as associações
    @GetMapping
    public ResponseEntity<List<AtletaEquipe>> listarTodasAtletasEquipes() {
        List<AtletaEquipe> associacoes = atletaEquipeService.buscarTodasAtletasEquipes();
        if (associacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(associacoes);
    }

    // GET /atletas-equipes/{codAtleta}/{codEquipe} - Buscar associação por chave composta
    @GetMapping("/{codAtleta}/{codEquipe}")
    public ResponseEntity<AtletaEquipe> buscarAtletaEquipePorId(
            @PathVariable Integer codAtleta,
            @PathVariable Integer codEquipe) {

        AtletaEquipeId id = new AtletaEquipeId(codAtleta, codEquipe);
        Optional<AtletaEquipe> associacao = atletaEquipeService.buscarAtletaEquipePorId(id);

        if (associacao.isPresent()) {
            return ResponseEntity.ok(associacao.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /atletas-equipes/{codAtleta}/{codEquipe} - Excluir uma associação
    @DeleteMapping("/{codAtleta}/{codEquipe}")
    public ResponseEntity<Void> deletarAtletaEquipe(
            @PathVariable Integer codAtleta,
            @PathVariable Integer codEquipe) {

        AtletaEquipeId id = new AtletaEquipeId(codAtleta, codEquipe);
        boolean deletado = atletaEquipeService.deletarAtletaEquipe(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}