package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.EquipeTorneio;
import br.com.gestoresportivo.entity.EquipeTorneioId;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.repository.EquipeRepository;
import br.com.gestoresportivo.repository.EquipeTorneioRepository;
import br.com.gestoresportivo.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeTorneioService {

    @Autowired
    private EquipeTorneioRepository equipeTorneioRepository;

    @Autowired
    private EquipeRepository equipeRepository; // Para buscar a Equipe real

    @Autowired
    private TorneioRepository torneioRepository; // Para buscar o Torneio real

    // Métodos auxiliares para buscar entidades relacionadas
    private Equipe buscarEquipeExistente(Integer codEquipe) {
        return equipeRepository.findById(codEquipe)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada com ID: " + codEquipe));
    }

    private Torneio buscarTorneioExistente(Integer codTorneio) {
        return torneioRepository.findById(codTorneio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado com ID: " + codTorneio));
    }

    @Transactional
    public EquipeTorneio salvarEquipeTorneio(EquipeTorneio equipeTorneio) {
        // Verifica se a associação já existe antes de salvar
        if (equipeTorneioRepository.existsById(equipeTorneio.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta equipe já está inscrita neste torneio.");
        }

        // Assegura que as entidades Equipe e Torneio existam e estejam gerenciadas
        Equipe equipeGerenciada = buscarEquipeExistente(equipeTorneio.getEquipe().getId());
        Torneio torneioGerenciado = buscarTorneioExistente(equipeTorneio.getTorneio().getId());

        // A lógica da Trigger 'ValidarEquipeTorneioModalidade' pode ser replicada aqui no Service
        // para validação em nível de aplicação antes mesmo de tentar salvar no DB.
        if (!equipeGerenciada.getModalidade().getId().equals(torneioGerenciado.getModalidade().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A equipe e o torneio devem ser da mesma modalidade.");
        }

        equipeTorneio.setEquipe(equipeGerenciada);
        equipeTorneio.setTorneio(torneioGerenciado);

        return equipeTorneioRepository.save(equipeTorneio);
    }

    @Transactional(readOnly = true)
    public List<EquipeTorneio> buscarTodasEquipesTorneios() {
        return equipeTorneioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EquipeTorneio> buscarEquipeTorneioPorId(EquipeTorneioId id) {
        return equipeTorneioRepository.findById(id);
    }

    // Para atualizar, normalmente você não atualiza uma chave composta.
    // Você deletaria a antiga associação e criaria uma nova, se a chave mudar.
    // Se a tabela de junção tivesse atributos extras (ex: 'dataInscricao'), eles poderiam ser atualizados aqui.
    // Por enquanto, não há PUT para esta entidade, pois não tem atributos extras além da chave.

    @Transactional
    public boolean deletarEquipeTorneio(EquipeTorneioId id) {
        if (equipeTorneioRepository.existsById(id)) {
            equipeTorneioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}