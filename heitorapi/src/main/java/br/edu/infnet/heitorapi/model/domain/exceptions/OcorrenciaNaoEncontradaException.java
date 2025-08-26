package br.edu.infnet.heitorapi.model.domain.exceptions;

public class OcorrenciaNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OcorrenciaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

