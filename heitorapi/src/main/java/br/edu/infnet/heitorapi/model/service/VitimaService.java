package br.edu.infnet.heitorapi.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.infnet.heitorapi.model.domain.Vitima;
import br.edu.infnet.heitorapi.model.repository.VitimaRepository;
import jakarta.transaction.Transactional;

@Service
public class VitimaService implements CrudService<Vitima, Integer> {

    private final VitimaRepository vitimaRepository;

    public VitimaService(VitimaRepository vitimaRepository) {
        this.vitimaRepository = vitimaRepository;
    }

    private void validar(Vitima vitima) {
        if (vitima == null) {
            throw new IllegalArgumentException("A vítima não pode estar nula!");
        }

        if (vitima.getNome() == null || vitima.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da vítima é uma informação obrigatória!");
        }

        if (vitima.getIdade() < 0 || vitima.getIdade() > 120) {
            throw new IllegalArgumentException("A idade da vítima deve estar entre 0 e 120 anos!");
        }
    }

    @Override
    @Transactional
    public Vitima incluir(Vitima vitima) {
        validar(vitima);

        if (vitima.getId() != null && vitima.getId() > 0) {
            throw new IllegalArgumentException("Uma nova vítima não pode ter um ID na inclusão!");
        }

        return vitimaRepository.save(vitima);
    }

    @Override
    @Transactional
    public Vitima alterar(Integer id, Vitima vitima) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("O ID para alteração não pode ser nulo/zero!");
        }

        validar(vitima);
        obterPorId(id);
        vitima.setId(id);

        return vitimaRepository.save(vitima);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        Vitima vitima = obterPorId(id);
        vitimaRepository.delete(vitima);
    }

    @Override
    public List<Vitima> obterLista() {
        return vitimaRepository.findAll();
    }

    @Override
    public Vitima obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID para consulta não pode ser nulo/zero!");
        }

        return vitimaRepository.findById(id).orElseThrow(() ->
            new RuntimeException("A vítima com ID " + id + " não foi encontrada!"));
    }

    // Métodos que podem ser implementados no futuro // expostos via API

    public List<Vitima> obterComDefensor() {
        return vitimaRepository.findByTemDefensor(true);
    }

    public List<Vitima> obterSemDefensor() {
        return vitimaRepository.findByTemDefensor(false);
    }

    public List<Vitima> obterPorProfissao(String profissao) {
        return vitimaRepository.findByProfissao(profissao);
    }

    public List<Vitima> obterPorFaixaEtaria(int idadeMin, int idadeMax) {
        return vitimaRepository.findByIdadeBetween(idadeMin, idadeMax);
    }
}

