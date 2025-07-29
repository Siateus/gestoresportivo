package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Integer> {

    Optional<Atleta> findByCpf(String cpf);
}
