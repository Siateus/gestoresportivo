package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    // Métodos CRUD básicos já vêm do JpaRepository.
    // Adicione métodos personalizados se precisar (ex: buscar jogos por data, por torneio, etc.)
}