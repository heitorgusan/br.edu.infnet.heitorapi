package br.edu.infnet.heitorapi.model.service;

import java.util.List;

public interface CrudService<T, ID> {

    T incluir(T entity);
    T alterar(ID id, T entity);
    T obterPorId(ID id);
    void excluir(ID id);
    List<T> obterLista();
}

