package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Integer> {

    Atleta findByCpf(String cpf);
}
