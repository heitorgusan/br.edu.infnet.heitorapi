package br.edu.infnet.heitorapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.heitorapi.model.domain.Vitima;

@Repository
public interface VitimaRepository extends JpaRepository<Vitima, Integer> {

    List<Vitima> findByTemDefensor(boolean temDefensor);
    
    List<Vitima> findByProfissao(String profissao);
    
    List<Vitima> findByIdadeBetween(int idadeMin, int idadeMax);
}

