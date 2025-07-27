package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.EquipeDTO;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.service.EquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipes") // Mapeia para o caminho base /equipes
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    // POST /equipes - Criar uma nova equipe
    @PostMapping
    public ResponseEntity<Equipe> criarEquipe(@RequestBody @Valid EquipeDTO equipeDTO) {
        // Cria uma instância de Modalidade com o ID recebido no DTO.
        // O service validará se a modalidade existe e a anexará ao contexto de persistência.
        Modalidade modalidade = new Modalidade();
        modalidade.setId(equipeDTO.getCodModalidade());

        Equipe equipe = new Equipe(
                equipeDTO.getNome(),
                equipeDTO.getSigla(),
                modalidade // Passa a entidade Modalidade (com apenas o ID preenchido)
        );
        Equipe equipeSalva = equipeService.salvarEquipe(equipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipeSalva);
    }

    // GET /equipes - Listar todas as equipes
    @GetMapping
    public ResponseEntity<List<Equipe>> listarTodasEquipes() {
        List<Equipe> equipes = equipeService.buscarTodasEquipes();
        if (equipes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se não houver dados
        }
        return ResponseEntity.ok(equipes); // 200 OK com a lista
    }

    // GET /equipes/{id} - Buscar equipe por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarEquipePorId(@PathVariable Integer id) {
        Optional<Equipe> equipe = equipeService.buscarEquipePorId(id);
        if (equipe.isPresent()) {
            return ResponseEntity.ok(equipe.get()); // 200 OK com a equipe
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome")
    public ResponseEntity<Equipe> buscarEquipePorNome(@RequestParam String nome) {
        Optional<Equipe> equipe = equipeService.buscarEquipePorNome(nome);
        return equipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /equipes/{id} - Atualizar uma equipe existente
    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizarEquipe(@PathVariable Integer id, @RequestBody @Valid EquipeDTO equipeDTO) {
        // Cria uma instância de Modalidade com o ID recebido no DTO, se fornecido.
        // O service validará se a modalidade existe e anexará ao contexto.
        Modalidade modalidade = null;
        if (equipeDTO.getCodModalidade() != null) {
            modalidade = new Modalidade();
            modalidade.setId(equipeDTO.getCodModalidade());
        }

        Equipe equipeParaAtualizar = new Equipe(
                equipeDTO.getNome(),
                equipeDTO.getSigla(),
                modalidade // Passa a entidade Modalidade (com ID)
        );

        Optional<Equipe> equipeAtualizada = equipeService.atualizarEquipe(id, equipeParaAtualizar);

        if (equipeAtualizada.isPresent()) {
            return ResponseEntity.ok(equipeAtualizada.get()); // 200 OK com a equipe atualizada
        }
        return ResponseEntity.notFound().build(); // 404 Not Found se não existir
    }

    // DELETE /equipes/{id} - Excluir uma equipe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEquipe(@PathVariable Integer id) {
        boolean deletado = equipeService.deletarEquipe(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content para exclusão bem-sucedida
        }
        return ResponseEntity.notFound().build(); // 404 Not Found se não existir
    }
}