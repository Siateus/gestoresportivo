package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.service.AtletaService;
import br.com.gestoresportivo.service.dto.AtletaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    // Endpoint POST para cadastrar atleta
    @PostMapping
    public ResponseEntity<Atleta> atleta(@RequestBody @Valid AtletaDTO atletaDTO) {
        Atleta atleta = new Atleta(
                atletaDTO.getNome(),
                atletaDTO.getCpf(),
                (atletaDTO.getSexo() != null && !atletaDTO.getSexo().isEmpty()) ? atletaDTO.getSexo().charAt(0) : null,
                atletaDTO.getCidade(),
                atletaDTO.getDataNascimento()
        );

        Atleta atletaSalvo = atletaService.salvarAtleta(atleta);

        return ResponseEntity.status(HttpStatus.CREATED).body(atletaSalvo);
    }

    // Endpoint GET para buscar atleta por id
    @GetMapping("/{id}")
    public ResponseEntity<Atleta> buscarAtletaPorId(@PathVariable Integer id) {
        Optional<Atleta> atleta = atletaService.buscarAtletaPorId(id);
        if (atleta.isPresent()) {
            return ResponseEntity.ok(atleta.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint GET para buscar atleta por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Optional<Atleta>> buscarAtletaPorCpf(@PathVariable String cpf) {
        Optional<Atleta> atleta = atletaService.buscarAtletaPorCpf(cpf);
        if (atleta != null) {
            return ResponseEntity.ok(atleta);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint PUT para atualizar atleta por id
    @PutMapping("/{id}")
    public ResponseEntity<Atleta> atualizarAtleta(@PathVariable Integer id, @RequestBody @Valid AtletaDTO atletaDTO) {
        Atleta atletaParaAtualizar = new Atleta(
                atletaDTO.getNome(),
                atletaDTO.getCpf(),
                (atletaDTO.getSexo() != null && !atletaDTO.getSexo().isEmpty()) ? atletaDTO.getSexo().charAt(0) : null,
                atletaDTO.getCidade(),
                atletaDTO.getDataNascimento()
        );

        Optional<Atleta> atletaAtualizado = atletaService.atualizarAtleta(id, atletaParaAtualizar);

        if (atletaAtualizado.isPresent()) {
            return ResponseEntity.ok(atletaAtualizado.get());
        }
        return ResponseEntity.notFound().build();
    }
    // Endpoint GET para buscar todos os atletas
    @GetMapping
    public ResponseEntity<List<Atleta>> listarTodosAtletas() {
        List<Atleta> atletas = atletaService.buscarTodosAtletas();
        if (atletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atletas);
    }

    // Endpoint DELETE para delatar atleta por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtleta(@PathVariable Integer id) {
        boolean deletado = atletaService.deletarAtleta(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



