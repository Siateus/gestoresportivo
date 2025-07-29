package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.JogoDTO;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.Jogo;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.service.JogoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    // POST /jogos - Criar um novo jogo
    @PostMapping
    public ResponseEntity<Jogo> criarJogo(@RequestBody @Valid JogoDTO jogoDTO) {

        Equipe equipe1 = new Equipe();
        equipe1.setId(jogoDTO.getCodEquipe1());

        Equipe equipe2 = new Equipe();
        equipe2.setId(jogoDTO.getCodEquipe2());

        Torneio torneio = new Torneio();
        torneio.setId(jogoDTO.getCodTorneio());

        Jogo jogo = new Jogo(
                jogoDTO.getData(),
                jogoDTO.getHora(),
                jogoDTO.getpTime1(),
                jogoDTO.getpTime2(),
                jogoDTO.getPlacar(),
                equipe1,
                equipe2,
                torneio
        );
        Jogo jogoSalvo = jogoService.salvarJogo(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(jogoSalvo);
    }

    // GET /jogos - Listar todos os jogos
    @GetMapping
    public ResponseEntity<List<Jogo>> listarTodosJogos() {
        List<Jogo> jogos = jogoService.buscarTodosJogos();
        if (jogos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se não houver dados
        }
        return ResponseEntity.ok(jogos); // 200 OK com a lista
    }

    // GET /jogos/{id} - Buscar jogo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarJogoPorId(@PathVariable Integer id) {
        Optional<Jogo> jogo = jogoService.buscarJogoPorId(id);
        if (jogo.isPresent()) {
            return ResponseEntity.ok(jogo.get());
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /jogos/{id} - Atualizar um jogo existente
    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizarJogo(@PathVariable Integer id, @RequestBody @Valid JogoDTO jogoDTO) {
        Equipe equipe1 = null;
        if (jogoDTO.getCodEquipe1() != null) {
            equipe1 = new Equipe();
            equipe1.setId(jogoDTO.getCodEquipe1());
        }
        Equipe equipe2 = null;
        if (jogoDTO.getCodEquipe2() != null) {
            equipe2 = new Equipe();
            equipe2.setId(jogoDTO.getCodEquipe2());
        }
        Torneio torneio = null;
        if (jogoDTO.getCodTorneio() != null) {
            torneio = new Torneio();
            torneio.setId(jogoDTO.getCodTorneio());
        }

        Jogo jogoParaAtualizar = new Jogo(
                jogoDTO.getData(),
                jogoDTO.getHora(),
                jogoDTO.getpTime1(),
                jogoDTO.getpTime2(),
                jogoDTO.getPlacar(),
                equipe1,
                equipe2,
                torneio
        );

        Optional<Jogo> jogoAtualizado = jogoService.atualizarJogo(id, jogoParaAtualizar);

        if (jogoAtualizado.isPresent()) {
            return ResponseEntity.ok(jogoAtualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /jogos/{id} - Excluir um jogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Integer id) {
        boolean deletado = jogoService.deletarJogo(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}