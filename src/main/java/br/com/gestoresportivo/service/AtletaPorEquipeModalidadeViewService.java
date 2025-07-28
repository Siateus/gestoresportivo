package br.com.gestoresportivo.service;

import br.com.gestoresportivo.service.dto.AtletaPorEquipeModalidadeResponseDTO; // Importe o DTO
import jakarta.persistence.EntityManager; // Importe EntityManager
import jakarta.persistence.Query;        // Importe Query
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtletaPorEquipeModalidadeViewService {

    @Autowired
    private EntityManager entityManager; // Injete o EntityManager

    @Transactional(readOnly = true)
    public List<AtletaPorEquipeModalidadeResponseDTO> buscarTodosAtletasPorEquipeModalidade() {
        // Use createNativeQuery para chamar a VIEW
        Query query = entityManager.createNativeQuery("SELECT nome_atleta, nome_equipe, nome_modalidade FROM atletas_por_equipe_modalidade");

        List<Object[]> results = query.getResultList(); // Obtenha os resultados como um List<Object[]>

        // Mapeie os resultados para o DTO
        List<AtletaPorEquipeModalidadeResponseDTO> dtoList = results.stream()
                .map(row -> new AtletaPorEquipeModalidadeResponseDTO(
                        (String) row[0], // nome_atleta
                        (String) row[1], // nome_equipe
                        (String) row[2]  // nome_modalidade
                ))
                .collect(Collectors.toList());

        return dtoList;
    }
}