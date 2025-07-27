package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Transactional
    public Atleta salvarAtleta(Atleta atleta) {
        return atletaRepository.save(atleta);
    }

    @Transactional(readOnly = true)
    public Optional<Atleta> buscarAtletaPorId(Integer id) {
        return atletaRepository.findById(id);
    }

    @Transactional
    public boolean deletarAtleta(Integer id) {
        if (atletaRepository.existsById(id)) {
            atletaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Atleta buscarAtletaPorCpf(String cpf) {
        return atletaRepository.findByCpf(cpf);
    }

    @Transactional(readOnly = true)
    public List<Atleta> buscarTodosAtletas() {
        return atletaRepository.findAll();
    }

    @Transactional
    public Optional<Atleta> atualizarAtleta(Integer id, Atleta atletaAtualizado) {
        Optional<Atleta> atletaExistente = atletaRepository.findById(id);

        if (atletaExistente.isPresent()) {
            Atleta atleta = atletaExistente.get();

            if (atletaAtualizado.getNome() != null) {
                atleta.setNome(atletaAtualizado.getNome());
            }
            if (atletaAtualizado.getCpf() != null) {
                atleta.setCpf(atletaAtualizado.getCpf());
            }
            if (atletaAtualizado.getSexo() != null) {
                atleta.setSexo(atletaAtualizado.getSexo());
            }
            if (atletaAtualizado.getCidade() != null) {
                atleta.setCidade(atletaAtualizado.getCidade());
            }
            if (atletaAtualizado.getDataNascimento() != null) {
                atleta.setDataNascimento(atletaAtualizado.getDataNascimento());
            }

            return Optional.of(atletaRepository.save(atleta));
        } else {
            return Optional.empty();
        }
    }
}
