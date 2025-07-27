package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Integer> {
    Optional<Modalidade> findByNome(String nome);
}