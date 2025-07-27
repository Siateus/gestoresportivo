package br.com.gestoresportivo.service;

import br.com.gestoresportivo.repository.AtletaRepository;
import br.com.gestoresportivo.service.dto.AtletaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AtletaService {

    @Autowired
    private AtletaRepository atletaRepository;

    public AtletaDTO salvarAtleta(@RequestBody AtletaDTO atletaDTO) {

    }


}
