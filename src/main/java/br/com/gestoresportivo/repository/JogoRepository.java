package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    boolean existsByTorneioId(Integer torneioId); // Spring Data JPA entende 'byTorneioId'
    List<Jogo> findByTorneioId(Integer torneioId);
}