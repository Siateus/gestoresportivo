package br.com.gestoresportivo.controller;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.service.AtletaService;
import br.com.gestoresportivo.service.dto.AtletaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @PostMapping
    public ResponseEntity<Atleta> atleta(@RequestBody @Valid AtletaDTO atletaDTO) {
        Atleta atleta = new Atleta(
                atletaDTO.getNome(),
                atletaDTO.getCpf(),
                atletaDTO.getSexo(),
                atletaDTO.getCidade(),
                atletaDTO.getDataNascimento()
        );

        Atleta atletaSalvo = atletaService.salvarAtleta(atleta);

        return ResponseEntity.status(HttpStatus.CREATED).body(atletaSalvo);
    }
}
