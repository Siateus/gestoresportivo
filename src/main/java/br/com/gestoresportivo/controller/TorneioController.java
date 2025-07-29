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
        Modalidade modalidade = new Modalidade();
        modalidade.setId(torneioDTO.getCodModalidade());

        Torneio torneio = new Torneio(
                torneioDTO.getNome(),
                torneioDTO.getQtdEquipe(),
                modalidade
        );
        Torneio torneioSalvo = torneioService.salvarTorneio(torneio);
        return ResponseEntity.status(HttpStatus.CREATED).body(torneioSalvo);
    }

    // GET /torneios - Listar todos os torneios
    @GetMapping
    public ResponseEntity<List<Torneio>> listarTodosTorneios() {
        List<Torneio> torneios = torneioService.buscarTodosTorneios();
        if (torneios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(torneios);
    }

    // GET /torneios/{id} - Buscar torneio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Torneio> buscarTorneioPorId(@PathVariable Integer id) {
        Optional<Torneio> torneio = torneioService.buscarTorneioPorId(id);
        if (torneio.isPresent()) {
            return ResponseEntity.ok(torneio.get());
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /torneios/{id} - Atualizar um torneio existente
    @PutMapping("/{id}")
    public ResponseEntity<Torneio> atualizarTorneio(@PathVariable Integer id, @RequestBody @Valid TorneioDTO torneioDTO) {
        Modalidade modalidade = null;
        if (torneioDTO.getCodModalidade() != null) {
            modalidade = new Modalidade();
            modalidade.setId(torneioDTO.getCodModalidade());
        }

        Torneio torneioParaAtualizar = new Torneio(
                torneioDTO.getNome(),
                torneioDTO.getQtdEquipe(),
                modalidade
        );

        Optional<Torneio> torneioAtualizado = torneioService.atualizarTorneio(id, torneioParaAtualizar);

        if (torneioAtualizado.isPresent()) {
            return ResponseEntity.ok(torneioAtualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /torneios/{id} - Excluir um torneio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTorneio(@PathVariable Integer id) {
        boolean deletado = torneioService.deletarTorneio(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}