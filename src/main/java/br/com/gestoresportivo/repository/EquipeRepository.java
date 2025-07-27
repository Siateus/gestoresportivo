// src/main/java/br/com/gestoresportivo/repository/EquipeRepository.java
package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
    Optional<Equipe> findByNome(String nome);
}