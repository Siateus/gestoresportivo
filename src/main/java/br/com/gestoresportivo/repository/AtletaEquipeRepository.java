package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.AtletaEquipe;
import br.com.gestoresportivo.entity.AtletaEquipeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtletaEquipeRepository extends JpaRepository<AtletaEquipe, AtletaEquipeId> {

    List<AtletaEquipe> findByIdCodAtleta(Integer codAtleta);
    boolean existsByIdCodAtleta(Integer codAtleta);
}