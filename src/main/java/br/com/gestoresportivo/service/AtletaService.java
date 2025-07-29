package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.repository.AtletaRepository;
import br.com.gestoresportivo.repository.AtletaEquipeRepository; // <--- Importar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private AtletaEquipeRepository atletaEquipeRepository;

    @Transactional
    public Atleta salvarAtleta(Atleta atleta) {
        Optional<Atleta> atletaExistentePorCpf = atletaRepository.findByCpf(atleta.getCpf());
        if (atletaExistentePorCpf.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF '" + atleta.getCpf() + "' já cadastrado para outro atleta.");
        }

        return atletaRepository.save(atleta);
    }

    @Transactional(readOnly = true)
    public Optional<Atleta> buscarAtletaPorId(Integer id) {
        return atletaRepository.findById(id);
    }

    @Transactional
    public boolean deletarAtleta(Integer id) {
        if (!atletaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atleta com ID " + id + " não encontrado.");
        }

        // Verifica se o atleta está associado a alguma equipe
        if (atletaEquipeRepository.existsByIdCodAtleta(id)) { // Usando o método que criamos
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar o Atleta com ID " + id + " pois ele está associado a uma equipe.");
        }

        atletaRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Optional<Atleta> buscarAtletaPorCpf(String cpf) {
        return atletaRepository.findByCpf(cpf);
    }

    @Transactional(readOnly = true)
    public List<Atleta> buscarTodosAtletas() {
        return atletaRepository.findAll();
    }

    @Transactional
    public Optional<Atleta> atualizarAtleta(Integer id, Atleta atletaAtualizado) {
        Optional<Atleta> atletaExistenteOpt = atletaRepository.findById(id);

        if (atletaExistenteOpt.isPresent()) {
            Atleta atleta = atletaExistenteOpt.get();

            // Verifica se o CPF foi alterado E se o novo CPF já existe para outro atleta
            if (atletaAtualizado.getCpf() != null && !atletaAtualizado.getCpf().equals(atleta.getCpf())) {
                Optional<Atleta> atletaComNovoCpf = atletaRepository.findByCpf(atletaAtualizado.getCpf());
                // Se encontrou um atleta com o novo CPF E NÃO É o próprio atleta que está sendo atualizado
                if (atletaComNovoCpf.isPresent() && !atletaComNovoCpf.get().getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Novo CPF '" + atletaAtualizado.getCpf() + "' já cadastrado para outro atleta.");
                }
            }

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