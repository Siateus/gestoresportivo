package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Jogo;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.repository.JogoRepository;
import br.com.gestoresportivo.repository.EquipeRepository;
import br.com.gestoresportivo.repository.TorneioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private TorneioRepository torneioRepository;

    @Autowired
    private EntityManager entityManager;

    private Equipe buscarEquipeExistente(Integer codEquipe) {
        if (codEquipe == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da Equipe não pode ser nulo.");
        }
        return equipeRepository.findById(codEquipe)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada com ID: " + codEquipe));
    }

    private Torneio buscarTorneioExistente(Integer codTorneio) {
        if (codTorneio == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do Torneio não pode ser nulo.");
        }
        return torneioRepository.findById(codTorneio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado com ID: " + codTorneio));
    }

    @Transactional
    public Jogo salvarJogo(Jogo jogo) {
        Equipe equipe1Gerenciada = buscarEquipeExistente(jogo.getEquipe1().getId());
        jogo.setEquipe1(equipe1Gerenciada);

        Equipe equipe2Gerenciada = buscarEquipeExistente(jogo.getEquipe2().getId());
        jogo.setEquipe2(equipe2Gerenciada);

        Torneio torneioGerenciado = buscarTorneioExistente(jogo.getTorneio().getId());
        jogo.setTorneio(torneioGerenciado);

        if (jogo.getPlacar() == null || jogo.getPlacar().isEmpty()) {
            // Cria a query nativa usando parâmetros POSICIONAIS (?)
            Query query = entityManager.createNativeQuery("SELECT gerar_placar_jogo(?, ?)");
            // Define os parâmetros pela posição (1 para o primeiro ?, 2 para o segundo ?)
            query.setParameter(1, jogo.getpTime1());
            query.setParameter(2, jogo.getpTime2());

            String placarGerado = (String) query.getSingleResult();
            jogo.setPlacar(placarGerado);
        }

        return jogoRepository.save(jogo);
    }

    @Transactional(readOnly = true)
    public List<Jogo> buscarTodosJogos() {
        return jogoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Jogo> buscarJogoPorId(Integer id) {
        return jogoRepository.findById(id);
    }

    @Transactional
    public Optional<Jogo> atualizarJogo(Integer id, Jogo jogoAtualizado) {
        Optional<Jogo> jogoExistente = jogoRepository.findById(id);

        if (jogoExistente.isPresent()) {
            Jogo jogo = jogoExistente.get();

            // Atualiza campos
            if (jogoAtualizado.getData() != null) {
                jogo.setData(jogoAtualizado.getData());
            }
            if (jogoAtualizado.getHora() != null) {
                jogo.setHora(jogoAtualizado.getHora());
            }
            if (jogoAtualizado.getpTime1() != null) {
                jogo.setpTime1(jogoAtualizado.getpTime1());
            }
            if (jogoAtualizado.getpTime2() != null) {
                jogo.setpTime2(jogoAtualizado.getpTime2());
            }
            if (jogoAtualizado.getPlacar() != null) {
                jogo.setPlacar(jogoAtualizado.getPlacar());
            }

            // Atualiza relacionamentos (se IDs forem fornecidos)
            if (jogoAtualizado.getEquipe1() != null && jogoAtualizado.getEquipe1().getId() != null) {
                Equipe equipe1Gerenciada = buscarEquipeExistente(jogoAtualizado.getEquipe1().getId());
                jogo.setEquipe1(equipe1Gerenciada);
            }
            if (jogoAtualizado.getEquipe2() != null && jogoAtualizado.getEquipe2().getId() != null) {
                Equipe equipe2Gerenciada = buscarEquipeExistente(jogoAtualizado.getEquipe2().getId());
                jogo.setEquipe2(equipe2Gerenciada);
            }
            if (jogoAtualizado.getTorneio() != null && jogoAtualizado.getTorneio().getId() != null) {
                Torneio torneioGerenciado = buscarTorneioExistente(jogoAtualizado.getTorneio().getId());
                jogo.setTorneio(torneioGerenciado);
            }

            return Optional.of(jogoRepository.save(jogo));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean deletarJogo(Integer id) {
        if (jogoRepository.existsById(id)) {
            jogoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}