package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.entity.Modalidade; // Importa a entidade Modalidade
import br.com.gestoresportivo.repository.EquipeRepository;
import br.com.gestoresportivo.repository.ModalidadeRepository; // Importa o repositório de Modalidade
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException; // Para lançar exceções HTTP

import java.util.List;
import java.util.Optional;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private ModalidadeRepository modalidadeRepository; // Injeta o repositório de Modalidade

    // Método auxiliar para buscar e validar a Modalidade
    private Modalidade buscarModalidadeExistente(Integer codModalidade) {
        if (codModalidade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da Modalidade não pode ser nulo.");
        }
        return modalidadeRepository.findById(codModalidade)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modalidade não encontrada com ID: " + codModalidade));
    }

    @Transactional // Garante que a operação seja transacional (atomica)
    public Equipe salvarEquipe(Equipe equipe) {
        // Assegura que a modalidade referenciada existe e está gerenciada
        Modalidade modalidadeGerenciada = buscarModalidadeExistente(equipe.getModalidade().getId());
        equipe.setModalidade(modalidadeGerenciada);

        return equipeRepository.save(equipe);
    }

    @Transactional(readOnly = true) // Otimiza transações para leitura
    public List<Equipe> buscarTodasEquipes() {
        return equipeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Equipe> buscarEquipePorId(Integer id) {
        return equipeRepository.findById(id);
    }

     @Transactional(readOnly = true)
     public Optional<Equipe> buscarEquipePorNome(String nome) {
         return equipeRepository.findByNome(nome);
     }

    @Transactional
    public Optional<Equipe> atualizarEquipe(Integer id, Equipe equipeAtualizada) {
        Optional<Equipe> equipeExistente = equipeRepository.findById(id);

        if (equipeExistente.isPresent()) {
            Equipe equipe = equipeExistente.get();

            // Atualiza campos
            if (equipeAtualizada.getNome() != null) {
                equipe.setNome(equipeAtualizada.getNome());
            }
            if (equipeAtualizada.getSigla() != null) {
                equipe.setSigla(equipeAtualizada.getSigla());
            }
            // Lógica para atualizar a modalidade
            if (equipeAtualizada.getModalidade() != null && equipeAtualizada.getModalidade().getId() != null) {
                Modalidade modalidadeGerenciada = buscarModalidadeExistente(equipeAtualizada.getModalidade().getId());
                equipe.setModalidade(modalidadeGerenciada);
            } else if (equipeAtualizada.getModalidade() == null) {
                // Decida se permitir null aqui para 'modalidade' ou se requer o ID sempre
                // Por exemplo, se null for enviado, mantém a modalidade existente ou lança erro.
                // Neste caso, se equipeAtualizada.getModalidade() for null, não atualiza o relacionamento.
            }

            return Optional.of(equipeRepository.save(equipe));
        } else {
            return Optional.empty(); // Equipe não encontrada
        }
    }

    @Transactional
    public boolean deletarEquipe(Integer id) {
        if (equipeRepository.existsById(id)) {
            // CUIDADO: Se houver equipes_torneio ou atleta_equipe que referenciam esta equipe,
            // você pode ter erros de integridade referencial.
            // O ideal é configurar o CASCADE (no @OneToMany) ou lidar com isso aqui
            // (ex: remover registros de ligação primeiro).
            equipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}