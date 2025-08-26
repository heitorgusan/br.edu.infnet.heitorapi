package br.edu.infnet.heitorapi.model.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.heitorapi.model.domain.OcorrenciaCriminal;
import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaInvalidaException;
import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaNaoEncontradaException;
import br.edu.infnet.heitorapi.model.repository.OcorrenciaCriminalRepository;
import jakarta.transaction.Transactional;

@Service
public class OcorrenciaCriminalService implements CrudService<OcorrenciaCriminal, Integer> {

    private final OcorrenciaCriminalRepository ocorrenciaRepository;
    private final Map<String, Integer> estatisticasBairro = new ConcurrentHashMap<>();

    public OcorrenciaCriminalService(OcorrenciaCriminalRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    private void validar(OcorrenciaCriminal ocorrencia) {
        if (ocorrencia == null) {
            throw new IllegalArgumentException("A ocorrência não pode estar nula!");
        }

        if (ocorrencia.getDescricao() == null || ocorrencia.getDescricao().trim().isEmpty()) {
            throw new OcorrenciaInvalidaException("A descrição da ocorrência é uma informação obrigatória!");
        }

        if (ocorrencia.getTipoDelito() == null) {
            throw new OcorrenciaInvalidaException("O tipo de delito é uma informação obrigatória!");
        }

        if (ocorrencia.getDataOcorrencia() == null) {
            throw new OcorrenciaInvalidaException("A data da ocorrência é uma informação obrigatória!");
        }
    }

    @Override
    @Transactional
    public OcorrenciaCriminal incluir(OcorrenciaCriminal ocorrencia) {

        validar(ocorrencia);

        if (ocorrencia.getId() != null && ocorrencia.getId() > 0) {
            throw new IllegalArgumentException("Uma nova ocorrência não pode ter um ID na inclusão!");
        }

        OcorrenciaCriminal novaOcorrencia = ocorrenciaRepository.save(ocorrencia);

        // Atualiza estatísticas do bairro
        if (novaOcorrencia.getEndereco() != null && novaOcorrencia.getEndereco().getBairro() != null) {
            String bairro = novaOcorrencia.getEndereco().getBairro();
            estatisticasBairro.put(bairro, estatisticasBairro.getOrDefault(bairro, 0) + 1);
        }

        return novaOcorrencia;
    }

    @Override
    @Transactional
    public OcorrenciaCriminal alterar(Integer id, OcorrenciaCriminal ocorrencia) {

        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(ocorrencia);

        obterPorId(id);

        ocorrencia.setId(id);

        return ocorrenciaRepository.save(ocorrencia);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {

        OcorrenciaCriminal ocorrencia = obterPorId(id);

        ocorrenciaRepository.delete(ocorrencia);
    }

    @Override
    public List<OcorrenciaCriminal> obterLista() {

        return ocorrenciaRepository.findAll();
    }

    @Override
    public OcorrenciaCriminal obterPorId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID para consulta não pode ser nulo/zero!");
        }

        return ocorrenciaRepository.findById(id).orElseThrow(() ->
            new OcorrenciaNaoEncontradaException("A ocorrência com ID " + id + " não foi encontrada!"));
    }

    public List<OcorrenciaCriminal> obterPorBairro(String bairro) {
        return ocorrenciaRepository.findByBairro(bairro);
    }

    public List<OcorrenciaCriminal> obterPorCidade(String cidade) {
        return ocorrenciaRepository.findByCidade(cidade);
    }

    // Métodos que podem ser implementados no futuro // expostos via API

    public List<OcorrenciaCriminal> obterPorTipoDelito(String tipoDelito) {
        return ocorrenciaRepository.findByTipoDelito(tipoDelito);
    }

    public List<OcorrenciaCriminal> obterPorGravidade(int gravidadeMinima) {
        return ocorrenciaRepository.findByGravidadeGreaterThanEqual(gravidadeMinima);
    }

    public List<OcorrenciaCriminal> obterNaoResolvidas() {
        return ocorrenciaRepository.findByResolvida(false);
    }

    public Map<String, Long> obterEstatisticasPorBairro() {
        List<OcorrenciaCriminal> todasOcorrencias = ocorrenciaRepository.findAll();

        return todasOcorrencias.stream()
            .filter(o -> o.getEndereco() != null && o.getEndereco().getBairro() != null)
            .collect(Collectors.groupingBy(
                o -> o.getEndereco().getBairro(),
                Collectors.counting()
            ));
    }

    public Double obterScorePericulosidade(String bairro) {
        List<OcorrenciaCriminal> ocorrenciasBairro = obterPorBairro(bairro);

        if (ocorrenciasBairro.isEmpty()) {
            return 0.0;
        }

        double somaGravidade = ocorrenciasBairro.stream()
            .mapToInt(OcorrenciaCriminal::getGravidade)
            .sum();

        return somaGravidade / ocorrenciasBairro.size();
    }
}

