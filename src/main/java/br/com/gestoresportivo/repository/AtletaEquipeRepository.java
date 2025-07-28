package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.AtletaEquipe;
import br.com.gestoresportivo.entity.AtletaEquipeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaEquipeRepository extends JpaRepository<AtletaEquipe, AtletaEquipeId> {
    // List<AtletaEquipe> findByIdCodEquipe(Integer codEquipe); // Busca todos os atletas em uma equipe
    // List<AtletaEquipe> findByIdCodAtleta(Integer codAtleta); // Busca todas as equipes de um atleta
}