package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Atleta, Integer> {
}
