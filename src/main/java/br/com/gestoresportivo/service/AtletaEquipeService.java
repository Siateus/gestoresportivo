package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Atleta;
import br.com.gestoresportivo.entity.AtletaEquipe;
import br.com.gestoresportivo.entity.AtletaEquipeId;
import br.com.gestoresportivo.entity.Equipe;
import br.com.gestoresportivo.repository.AtletaRepository;
import br.com.gestoresportivo.repository.AtletaEquipeRepository;
import br.com.gestoresportivo.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AtletaEquipeService {

    @Autowired
    private AtletaEquipeRepository atletaEquipeRepository;

    @Autowired
    private AtletaRepository atletaRepository; // Para buscar o Atleta real

    @Autowired
    private EquipeRepository equipeRepository; // Para buscar a Equipe real

    // Métodos auxiliares para buscar entidades relacionadas
    private Atleta buscarAtletaExistente(Integer codAtleta) {
        return atletaRepository.findById(codAtleta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atleta não encontrado com ID: " + codAtleta));
    }

    private Equipe buscarEquipeExistente(Integer codEquipe) {
        return equipeRepository.findById(codEquipe)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada com ID: " + codEquipe));
    }

    @Transactional
    public AtletaEquipe salvarAtletaEquipe(AtletaEquipe atletaEquipe) {
        // Verifica se a associação já existe antes de salvar
        if (atletaEquipeRepository.existsById(atletaEquipe.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este atleta já está nesta equipe.");
        }

        // Assegura que as entidades Atleta e Equipe existam e estejam gerenciadas
        Atleta atletaGerenciado = buscarAtletaExistente(atletaEquipe.getAtleta().getId());
        Equipe equipeGerenciada = buscarEquipeExistente(atletaEquipe.getEquipe().getId());

        // Lógica da Trigger 'ValidarAtletaEquipeModalidade' pode ser implementada aqui
        // (Impede que um atleta seja adicionado a mais de uma equipe na mesma modalidade.)
        // Para isso, você precisaria buscar todas as equipes do atleta e verificar suas modalidades.
        // Exemplo (simplificado):
        // List<AtletaEquipe> associacoesExistentesDoAtleta = atletaEquipeRepository.findByIdCodAtleta(atletaGerenciado.getId());
        // for (AtletaEquipe ae : associacoesExistentesDoAtleta) {
        //     if (ae.getEquipe().getModalidade().equals(equipeGerenciada.getModalidade())) {
        //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atleta já está em uma equipe desta modalidade.");
        //     }
        // }


        atletaEquipe.setAtleta(atletaGerenciado);
        atletaEquipe.setEquipe(equipeGerenciada);

        return atletaEquipeRepository.save(atletaEquipe);
    }

    @Transactional(readOnly = true)
    public List<AtletaEquipe> buscarTodasAtletasEquipes() {
        return atletaEquipeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AtletaEquipe> buscarAtletaEquipePorId(AtletaEquipeId id) {
        return atletaEquipeRepository.findById(id);
    }

    // Não é comum ter um PUT para esta entidade se ela não tiver atributos extras,
    // pois a chave composta já define a relação. Se a chave mudasse, seria DELETE + POST.

    @Transactional
    public boolean deletarAtletaEquipe(AtletaEquipeId id) {
        if (atletaEquipeRepository.existsById(id)) {
            atletaEquipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}