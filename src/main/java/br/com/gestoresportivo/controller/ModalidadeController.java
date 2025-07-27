package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.service.dto.ModalidadeDTO;
import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.service.ModalidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeController {

    @Autowired
    private ModalidadeService modalidadeService;


    @PostMapping
    public ResponseEntity<Modalidade> criarModalidade(@RequestBody @Valid ModalidadeDTO modalidadeDTO) {
        // Converte o DTO para a entidade Modalidade
        Modalidade modalidade = new Modalidade(
                modalidadeDTO.getNome(),
                modalidadeDTO.getQtdAtletaEquipe()
        );
        Modalidade modalidadeSalva = modalidadeService.salvarModalidade(modalidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(modalidadeSalva);
    }


    @GetMapping
    public ResponseEntity<List<Modalidade>> listarTodasModalidades() {
        List<Modalidade> modalidades = modalidadeService.buscarTodasModalidades();
        if (modalidades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modalidades);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Modalidade> buscarModalidadePorId(@PathVariable Integer id) {
        Optional<Modalidade> modalidade = modalidadeService.buscarModalidadePorId(id);
        if (modalidade.isPresent()) {
            return ResponseEntity.ok(modalidade.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Modalidade> atualizarModalidade(@PathVariable Integer id, @RequestBody @Valid ModalidadeDTO modalidadeDTO) {
        Modalidade modalidadeParaAtualizar = new Modalidade(
                modalidadeDTO.getNome(),
                modalidadeDTO.getQtdAtletaEquipe()
        );

        Optional<Modalidade> modalidadeAtualizada = modalidadeService.atualizarModalidade(id, modalidadeParaAtualizar);

        if (modalidadeAtualizada.isPresent()) {
            return ResponseEntity.ok(modalidadeAtualizada.get());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Integer id) {
        boolean deletado = modalidadeService.deletarModalidade(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}