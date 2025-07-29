// src/main/java/br/com/gestoresportivo/service/TorneioService.java
package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.repository.ModalidadeRepository;
import br.com.gestoresportivo.repository.TorneioRepository;
import br.com.gestoresportivo.repository.EquipeTorneioRepository; // Importar
import br.com.gestoresportivo.repository.JogoRepository; // Importar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TorneioService {

    @Autowired
    private TorneioRepository torneioRepository;

    @Autowired
    private ModalidadeRepository modalidadeRepository;

    @Autowired // Injetar o repositório de EquipeTorneio
    private EquipeTorneioRepository equipeTorneioRepository;

    @Autowired // Injetar o repositório de Jogo
    private JogoRepository jogoRepository;

    private Modalidade buscarModalidadeExistente(Integer codModalidade) {
        if (codModalidade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da Modalidade não pode ser nulo.");
        }
        return modalidadeRepository.findById(codModalidade)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modalidade não encontrada com ID: " + codModalidade));
    }

    @Transactional
    public Torneio salvarTorneio(Torneio torneio) {
        Modalidade modalidadeGerenciada = buscarModalidadeExistente(torneio.getModalidade().getId());
        torneio.setModalidade(modalidadeGerenciada);
        return torneioRepository.save(torneio);
    }

    @Transactional(readOnly = true)
    public List<Torneio> buscarTodosTorneios() {
        return torneioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Torneio> buscarTorneioPorId(Integer id) {
        return torneioRepository.findById(id);
    }

    @Transactional
    public Optional<Torneio> atualizarTorneio(Integer id, Torneio torneioAtualizado) {
        Optional<Torneio> torneioExistente = torneioRepository.findById(id);

        if (torneioExistente.isPresent()) {
            Torneio torneio = torneioExistente.get();

            if (torneioAtualizado.getNome() != null) {
                torneio.setNome(torneioAtualizado.getNome());
            }
            if (torneioAtualizado.getQtdEquipe() != null) {
                torneio.setQtdEquipe(torneioAtualizado.getQtdEquipe());
            }
            if (torneioAtualizado.getModalidade() != null && torneioAtualizado.getModalidade().getId() != null) {
                Modalidade modalidadeGerenciada = buscarModalidadeExistente(torneioAtualizado.getModalidade().getId());
                torneio.setModalidade(modalidadeGerenciada);
            }

            return Optional.of(torneioRepository.save(torneio));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean deletarTorneio(Integer id) {
        if (!torneioRepository.existsById(id)) {
            return false;
        }

        // 1. Verifica se existem associações em equipe_torneio
        if (equipeTorneioRepository.existsByIdCodTorneio(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar o Torneio com ID " + id + " pois ele possui equipes inscritas.");
        }

        // 2. Verifica se existem jogos vinculados a este torneio
        if (jogoRepository.existsByTorneioId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar o Torneio com ID " + id + " pois ele possui jogos vinculados.");
        }

        torneioRepository.deleteById(id);
        return true;
    }
}