// src/main/java/br/com/gestoresportivo/repository/EquipeTorneioRepository.java
package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.EquipeTorneio;
import br.com.gestoresportivo.entity.EquipeTorneioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importar @Query
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeTorneioRepository extends JpaRepository<EquipeTorneio, EquipeTorneioId> {

    // Método para buscar todas as associações, carregando Equipe e Torneio ansiosamente
    @Query("SELECT et FROM EquipeTorneio et JOIN FETCH et.equipe JOIN FETCH et.torneio")
    List<EquipeTorneio> findAllWithEquipeAndTorneio();

    // Método para buscar uma associação por ID, carregando Equipe e Torneio ansiosamente
    @Query("SELECT et FROM EquipeTorneio et JOIN FETCH et.equipe JOIN FETCH et.torneio WHERE et.id = :id")
    Optional<EquipeTorneio> findByIdWithEquipeAndTorneio(EquipeTorneioId id);
}