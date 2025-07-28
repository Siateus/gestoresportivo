package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneioRepository extends JpaRepository<Torneio, Integer> {
    // JpaRepository já fornece métodos como save(), findById(), findAll(), deleteById(), etc.
    // Adicione métodos de busca personalizados aqui, se necessário:
    // Optional<Torneio> findByNome(String nome);
}