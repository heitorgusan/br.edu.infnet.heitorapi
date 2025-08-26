package br.edu.infnet.heitorapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.infnet.heitorapi.model.domain.TipoDelito;

@Repository
public interface TipoDelitoRepository extends JpaRepository<TipoDelito, Integer> {

    // Buscar tipos de delito ativos
    List<TipoDelito> findByAtivoTrue();

    // Buscar por categoria
    List<TipoDelito> findByCategoria(String categoria);

    // Buscar por nome (case-insensitive)
    List<TipoDelito> findByNomeContainingIgnoreCase(String nome);

    // Buscar por faixa de gravidade
    @Query("SELECT t FROM TipoDelito t WHERE t.gravidadeMedia BETWEEN ?1 AND ?2 AND t.ativo = true")
    List<TipoDelito> findByGravidadeMediaBetween(int min, int max);
}
