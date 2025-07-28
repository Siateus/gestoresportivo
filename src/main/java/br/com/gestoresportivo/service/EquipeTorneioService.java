package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.EquipeTorneio;
import br.com.gestoresportivo.entity.EquipeTorneioId;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.repository.EquipeRepository;
import br.com.gestoresportivo.repository.EquipeTorneioRepository;
import br.com.gestoresportivo.repository.TorneioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
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
    private EquipeRepository equipeRepository;

    @Autowired
    private TorneioRepository torneioRepository;

    @Autowired
    private EntityManager entityManager; // Mantenha se for usar a procedure em algum lugar

    private Equipe buscarEquipeExistente(Integer codEquipe) {
        return equipeRepository.findById(codEquipe)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada com ID: " + codEquipe));
    }

    private Torneio buscarTorneioExistente(Integer codTorneio) {
        return torneioRepository.findById(codTorneio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado com ID: " + codTorneio));
    }

    @Transactional // Este método usa o JPA para salvar e validar
    public EquipeTorneio salvarEquipeTorneio(EquipeTorneio equipeTorneio) {
        // Assegura que as entidades Equipe e Torneio existam e estejam gerenciadas
        Equipe equipeGerenciada = buscarEquipeExistente(equipeTorneio.getEquipe().getId());
        Torneio torneioGerenciado = buscarTorneioExistente(equipeTorneio.getTorneio().getId());

        // Validação da regra de negócio: equipe e torneio devem ser da mesma modalidade
        if (!equipeGerenciada.getModalidade().getId().equals(torneioGerenciado.getModalidade().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A equipe e o torneio devem ser da mesma modalidade.");
        }

        // Verifica se a associação já existe (PK composta)
        if (equipeTorneioRepository.existsById(equipeTorneio.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta equipe já está inscrita neste torneio.");
        }

        equipeTorneio.setEquipe(equipeGerenciada);
        equipeTorneio.setTorneio(torneioGerenciado);

        // O JPA salva e retorna a entidade gerenciada
        return equipeTorneioRepository.save(equipeTorneio);
    }

    // Método para chamar procedure (se ainda for necessário para outros fins)
    @Transactional
    public void inscreverEquipeViaProcedure(Integer codEquipe, Integer codTorneio) {
        try {
            entityManager.createNativeQuery("CALL inscrever_equipe(?, ?)")
                    .setParameter(1, codEquipe)
                    .setParameter(2, codTorneio)
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao inscrever equipe via procedure: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<EquipeTorneio> buscarTodasEquipesTorneios() {
        // CHAMA O NOVO MÉTODO COM JOIN FETCH
        return equipeTorneioRepository.findAllWithEquipeAndTorneio();
    }

    @Transactional(readOnly = true)
    public Optional<EquipeTorneio> buscarEquipeTorneioPorId(EquipeTorneioId id) {
        // CHAMA O NOVO MÉTODO COM JOIN FETCH
        return equipeTorneioRepository.findByIdWithEquipeAndTorneio(id);
    }

    @Transactional
    public boolean deletarEquipeTorneio(EquipeTorneioId id) {
        if (equipeTorneioRepository.existsById(id)) {
            equipeTorneioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}