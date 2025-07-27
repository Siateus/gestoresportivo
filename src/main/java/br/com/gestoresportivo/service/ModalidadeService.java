package br.com.gestoresportivo.service;

import br.com.gestoresportivo.entity.Modalidade;
import br.com.gestoresportivo.repository.ModalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeService {

    @Autowired
    private ModalidadeRepository modalidadeRepository;

    @Transactional
    public Modalidade salvarModalidade(Modalidade modalidade) {
        return modalidadeRepository.save(modalidade);
    }

    @Transactional(readOnly = true)
    public List<Modalidade> buscarTodasModalidades() {
        return modalidadeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Modalidade> buscarModalidadePorId(Integer id) {
        return modalidadeRepository.findById(id);
    }

    @Transactional
    public Optional<Modalidade> atualizarModalidade(Integer id, Modalidade modalidadeAtualizada) {
        Optional<Modalidade> modalidadeExistente = modalidadeRepository.findById(id);

        if (modalidadeExistente.isPresent()) {
            Modalidade modalidade = modalidadeExistente.get();


            if (modalidadeAtualizada.getNome() != null) {
                modalidade.setNome(modalidadeAtualizada.getNome());
            }
            if (modalidadeAtualizada.getQtdAtletaEquipe() != null) {
                modalidade.setQtdAtletaEquipe(modalidadeAtualizada.getQtdAtletaEquipe());
            }

            return Optional.of(modalidadeRepository.save(modalidade));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean deletarModalidade(Integer id) {
        if (modalidadeRepository.existsById(id)) {
            modalidadeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}