package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.TorneioDTO;
import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.service.TorneioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/torneios")
public class TorneioController {

    @Autowired
    private TorneioService torneioService;

    // POST /torneios - Criar um novo torneio
    @PostMapping
    public ResponseEntity<Torneio> criarTorneio(@RequestBody @Valid TorneioDTO torneioDTO) {
        // Cria uma instância de Modalidade com o ID recebido no DTO.
        // O service validará se a modalidade existe e a anexará ao contexto de persistência.
        Modalidade modalidade = new Modalidade();
        modalidade.setId(torneioDTO.getCodModalidade());

        Torneio torneio = new Torneio(
                torneioDTO.getNome(),
                torneioDTO.getQtdEquipe(),
                modalidade // Passa a entidade Modalidade (com apenas o ID preenchido)
        );
        Torneio torneioSalvo = torneioService.salvarTorneio(torneio);
        return ResponseEntity.status(HttpStatus.CREATED).body(torneioSalvo);
    }

    // GET /torneios - Listar todos os torneios
    @GetMapping
    public ResponseEntity<List<Torneio>> listarTodosTorneios() {
        List<Torneio> torneios = torneioService.buscarTodosTorneios();
        if (torneios.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se não houver dados
        }
        return ResponseEntity.ok(torneios); // 200 OK com a lista
    }

    // GET /torneios/{id} - Buscar torneio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Torneio> buscarTorneioPorId(@PathVariable Integer id) {
        Optional<Torneio> torneio = torneioService.buscarTorneioPorId(id);
        if (torneio.isPresent()) {
            return ResponseEntity.ok(torneio.get()); // 200 OK com o torneio
        }
        return ResponseEntity.notFound().build(); // 404 Not Found se não encontrar
    }

    // PUT /torneios/{id} - Atualizar um torneio existente
    @PutMapping("/{id}")
    public ResponseEntity<Torneio> atualizarTorneio(@PathVariable Integer id, @RequestBody @Valid TorneioDTO torneioDTO) {
        // Cria uma instância de Modalidade com o ID recebido no DTO, se fornecido.
        // O service validará se a modalidade existe e anexará ao contexto.
        Modalidade modalidade = null;
        if (torneioDTO.getCodModalidade() != null) {
            modalidade = new Modalidade();
            modalidade.setId(torneioDTO.getCodModalidade());
        }

        Torneio torneioParaAtualizar = new Torneio(
                torneioDTO.getNome(),
                torneioDTO.getQtdEquipe(),
                modalidade // Passa a entidade Modalidade (com ID)
        );

        Optional<Torneio> torneioAtualizado = torneioService.atualizarTorneio(id, torneioParaAtualizar);

        if (torneioAtualizado.isPresent()) {
            return ResponseEntity.ok(torneioAtualizado.get()); // 200 OK com o torneio atualizado
        }
        return ResponseEntity.notFound().build(); // 404 Not Found se não existir
    }

    // DELETE /torneios/{id} - Excluir um torneio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTorneio(@PathVariable Integer id) {
        boolean deletado = torneioService.deletarTorneio(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content para exclusão bem-sucedida
        }
        return ResponseEntity.notFound().build(); // 404 Not Found se não existir
    }
}