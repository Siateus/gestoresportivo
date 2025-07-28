package br.com.gestoresportivo.repository;

import br.com.gestoresportivo.entity.EquipeTorneio;
import br.com.gestoresportivo.entity.EquipeTorneioId; // Importa a classe da chave composta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeTorneioRepository extends JpaRepository<EquipeTorneio, EquipeTorneioId> {

    // List<EquipeTorneio> findByIdCodTorneio(Integer codTorneio); // Busca todas as equipes em um torneio
}