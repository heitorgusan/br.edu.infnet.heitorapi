package br.edu.infnet.heitorapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.infnet.heitorapi.model.domain.OcorrenciaCriminal;

@Repository
public interface OcorrenciaCriminalRepository extends JpaRepository<OcorrenciaCriminal, Integer> {

    List<OcorrenciaCriminal> findByResolvida(boolean resolvida);

    @Query("SELECT o FROM OcorrenciaCriminal o WHERE o.tipoDelito.nome = :nomeTipoDelito")
    List<OcorrenciaCriminal> findByTipoDelito(@Param("nomeTipoDelito") String nomeTipoDelito);

    List<OcorrenciaCriminal> findByGravidadeGreaterThanEqual(int gravidade);

    @Query("SELECT o FROM OcorrenciaCriminal o WHERE o.endereco.bairro = :bairro")
    List<OcorrenciaCriminal> findByBairro(@Param("bairro") String bairro);

    @Query("SELECT o FROM OcorrenciaCriminal o WHERE o.endereco.cidade = :cidade")
    List<OcorrenciaCriminal> findByCidade(@Param("cidade") String cidade);

}

