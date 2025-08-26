package br.edu.infnet.heitorapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.heitorapi.model.domain.TipoDelito;
import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaInvalidaException;
import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaNaoEncontradaException;
import br.edu.infnet.heitorapi.model.repository.TipoDelitoRepository;
import jakarta.transaction.Transactional;

@Service
public class TipoDelitoService implements CrudService<TipoDelito, Integer> {

    private final TipoDelitoRepository tipoDelitoRepository;

    public TipoDelitoService(TipoDelitoRepository tipoDelitoRepository) {
        this.tipoDelitoRepository = tipoDelitoRepository;
    }

    private void validar(TipoDelito tipoDelito) {
        if (tipoDelito == null) {
            throw new IllegalArgumentException("O tipo de delito não pode estar nulo!");
        }

        if (tipoDelito.getNome() == null || tipoDelito.getNome().trim().isEmpty()) {
            throw new OcorrenciaInvalidaException("O nome do tipo de delito é uma informação obrigatória!");
        }

        if (tipoDelito.getCategoria() == null || tipoDelito.getCategoria().trim().isEmpty()) {
            throw new OcorrenciaInvalidaException("A categoria é uma informação obrigatória!");
        }

        if (tipoDelito.getGravidadeMedia() < 1 || tipoDelito.getGravidadeMedia() > 10) {
            throw new OcorrenciaInvalidaException("A gravidade média deve estar entre 1 e 10!");
        }
    }

    @Override
    @Transactional
    public TipoDelito incluir(TipoDelito tipoDelito) {
        validar(tipoDelito);
        return tipoDelitoRepository.save(tipoDelito);
    }

    @Override
    @Transactional
    public TipoDelito alterar(Integer id, TipoDelito tipoDelito) {
        TipoDelito existente = obterPorId(id);

        validar(tipoDelito);

        existente.setNome(tipoDelito.getNome());
        existente.setCategoria(tipoDelito.getCategoria());
        existente.setGravidadeMedia(tipoDelito.getGravidadeMedia());
        existente.setDescricao(tipoDelito.getDescricao());
        existente.setAtivo(tipoDelito.isAtivo());

        return tipoDelitoRepository.save(existente);
    }

    @Override
    public TipoDelito obterPorId(Integer id) {
        return tipoDelitoRepository.findById(id)
                .orElseThrow(() -> new OcorrenciaNaoEncontradaException("Tipo de delito não encontrado com ID: " + id));
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        TipoDelito tipoDelito = obterPorId(id);
        tipoDelitoRepository.delete(tipoDelito);
    }

    @Override
    public List<TipoDelito> obterLista() {
        return tipoDelitoRepository.findAll();
    }

    // Métodos que podem ser implementados no futuro // expostos via API

    public List<TipoDelito> obterTiposAtivos() {
        return tipoDelitoRepository.findByAtivoTrue();
    }

    public List<TipoDelito> obterPorCategoria(String categoria) {
        return tipoDelitoRepository.findByCategoria(categoria);
    }

    public TipoDelito obterOuCriarTipoDelito(String nome, String categoria, int gravidadeMedia) {
        List<TipoDelito> tipos = tipoDelitoRepository.findByNomeContainingIgnoreCase(nome);

        if (!tipos.isEmpty()) {
            return tipos.get(0);
        }

        // Criar novo tipo se não existir
        TipoDelito novoTipo = new TipoDelito();
        novoTipo.setNome(nome);
        novoTipo.setCategoria(categoria);
        novoTipo.setGravidadeMedia(gravidadeMedia);
        novoTipo.setDescricao("Tipo de delito criado automaticamente");
        novoTipo.setAtivo(true);

        return incluir(novoTipo);
    }
}
