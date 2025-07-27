package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtletaService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Transactional
    public Atleta salvarAtleta(Atleta atleta) {
        return atletaRepository.save(atleta);
    }


}
