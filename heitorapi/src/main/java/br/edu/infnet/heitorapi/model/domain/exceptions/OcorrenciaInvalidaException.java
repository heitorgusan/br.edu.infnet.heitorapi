package br.edu.infnet.heitorapi.model.domain.exceptions;

public class OcorrenciaInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OcorrenciaInvalidaException(String mensagem) {
        super(mensagem);
    }
}

