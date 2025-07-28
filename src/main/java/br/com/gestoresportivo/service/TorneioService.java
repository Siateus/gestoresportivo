package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.entity.Torneio;
import br.com.gestoresportivo.repository.ModalidadeRepository;
import br.com.gestoresportivo.repository.TorneioRepository;
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
    private ModalidadeRepository modalidadeRepository; // Injeta o repositório de Modalidade

    // Método auxiliar para buscar e validar a Modalidade
    private Modalidade buscarModalidadeExistente(Integer codModalidade) {
        if (codModalidade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da Modalidade não pode ser nulo.");
        }
        return modalidadeRepository.findById(codModalidade)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modalidade não encontrada com ID: " + codModalidade));
    }

    @Transactional // Garante que a operação seja transacional (atômica)
    public Torneio salvarTorneio(Torneio torneio) {
        // Assegura que a modalidade referenciada exista e esteja gerenciada
        Modalidade modalidadeGerenciada = buscarModalidadeExistente(torneio.getModalidade().getId());
        torneio.setModalidade(modalidadeGerenciada);

        return torneioRepository.save(torneio);
    }

    @Transactional(readOnly = true) // Otimiza transações para leitura
    public List<Torneio> buscarTodosTorneios() {
        return torneioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Torneio> buscarTorneioPorId(Integer id) {
        return torneioRepository.findById(id);
    }

    // Você pode adicionar métodos de busca personalizados aqui, por exemplo:
    // @Transactional(readOnly = true)
    // public Optional<Torneio> buscarTorneioPorNome(String nome) {
    //     return torneioRepository.findByNome(nome);
    // }

    @Transactional
    public Optional<Torneio> atualizarTorneio(Integer id, Torneio torneioAtualizado) {
        Optional<Torneio> torneioExistente = torneioRepository.findById(id);

        if (torneioExistente.isPresent()) {
            Torneio torneio = torneioExistente.get();

            // Atualiza campos
            if (torneioAtualizado.getNome() != null) {
                torneio.setNome(torneioAtualizado.getNome());
            }
            if (torneioAtualizado.getQtdEquipe() != null) {
                torneio.setQtdEquipe(torneioAtualizado.getQtdEquipe());
            }
            // Lógica para atualizar a modalidade
            if (torneioAtualizado.getModalidade() != null && torneioAtualizado.getModalidade().getId() != null) {
                Modalidade modalidadeGerenciada = buscarModalidadeExistente(torneioAtualizado.getModalidade().getId());
                torneio.setModalidade(modalidadeGerenciada);
            } else if (torneioAtualizado.getModalidade() == null) {
                // Decida se permitir null aqui para 'modalidade' ou se requer o ID sempre
            }

            return Optional.of(torneioRepository.save(torneio));
        } else {
            return Optional.empty(); // Torneio não encontrado
        }
    }

    @Transactional
    public boolean deletarTorneio(Integer id) {
        if (torneioRepository.existsById(id)) {
            // CUIDADO: Se houver EquipeTorneio ou Jogos que referenciam este torneio,
            // você pode ter erros de integridade referencial.
            // O ideal é configurar o CASCADE (no @OneToMany) ou lidar com isso aqui
            // (ex: remover registros de ligação primeiro).
            torneioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}