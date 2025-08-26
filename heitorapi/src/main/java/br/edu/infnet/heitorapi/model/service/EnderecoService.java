package br.edu.infnet.heitorapi.model.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import br.edu.infnet.heitorapi.client.ViaCepClient;
import br.edu.infnet.heitorapi.model.domain.Endereco;
import br.edu.infnet.heitorapi.model.dto.ViaCepResponseDto;

@Service
public class EnderecoService {

    private final ViaCepClient viaCepClient;

    // Health Check simples para ViaCEP
    private final AtomicInteger consecutiveFailures = new AtomicInteger(0);
    private final AtomicReference<LocalDateTime> lastFailureTime = new AtomicReference<>();

    private static final int MAX_CONSECUTIVE_FAILURES = 3;
    private static final int COOLDOWN_MINUTES = 5;

    public EnderecoService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public Endereco buscarEnderecoPorCep(String cep) {
        if (isViaCepDown()) {
            String message = String.format("ViaCEP temporariamente desabilitado após %d falhas consecutivas. Última falha: %s",
                consecutiveFailures.get(),
                lastFailureTime.get() != null ? lastFailureTime.get().toString() : "N/A");
            System.err.println(message);
            throw new RuntimeException("Serviço ViaCEP indisponível temporariamente");
        }

        try {
            String cepLimpo = cep.replaceAll("[^0-9]", "");

            if (cepLimpo.length() != 8) {
                throw new IllegalArgumentException("CEP deve conter 8 dígitos");
            }

            ViaCepResponseDto response = viaCepClient.buscarEnderecoPorCep(cepLimpo);

            if (response.temErro()) {
                recordFailure("CEP não encontrado: " + cep);
                throw new RuntimeException("CEP não encontrado: " + cep);
            }

            recordSuccess();
            return converterParaEndereco(response);

        } catch (RuntimeException e) {
            if (e.getMessage().contains("CEP não encontrado") || e.getMessage().contains("CEP deve conter")) {
                throw e;
            }
            recordFailure("Erro ao buscar CEP " + cep + ": " + e.getMessage());
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage());
        } catch (Exception e) {
            recordFailure("Erro inesperado ao buscar CEP " + cep + ": " + e.getMessage());
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage());
        }
    }

    private Endereco converterParaEndereco(ViaCepResponseDto dto) {
        Endereco endereco = new Endereco();

        endereco.setCep(formatarCep(dto.getCep()));
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getLocalidade());
        endereco.setUf(dto.getUf());

        return endereco;
    }

    private String formatarCep(String cep) {
        if (cep != null && cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }

    // HEALTH CHECK

    /**
     * Verifica se ViaCEP deve ser considerado "down" baseado em falhas consecutivas
     */
    private boolean isViaCepDown() {
        if (consecutiveFailures.get() < MAX_CONSECUTIVE_FAILURES) {
            return false;
        }

        LocalDateTime lastFailure = lastFailureTime.get();
        if (lastFailure == null) {
            return false;
        }

        long minutesSinceLastFailure = ChronoUnit.MINUTES.between(lastFailure, LocalDateTime.now());
        if (minutesSinceLastFailure >= COOLDOWN_MINUTES) {
            System.out.println(" ViaCEP: Tempo de cooldown atingido, tentando novamente...");
            return false;
        }

        return true;
    }

    /**
     * Registra uma falha do ViaCEP
     */
    private void recordFailure(String errorMessage) {
        int currentFailures = consecutiveFailures.incrementAndGet();
        lastFailureTime.set(LocalDateTime.now());

        System.err.println(String.format("ViaCEP ERRO [%d/%d]: %s",
            currentFailures, MAX_CONSECUTIVE_FAILURES, errorMessage));

        if (currentFailures >= MAX_CONSECUTIVE_FAILURES) {
            System.err.println(String.format("ViaCEP DESABILITADO após %d falhas consecutivas. Cooldown: %d minutos",
                MAX_CONSECUTIVE_FAILURES, COOLDOWN_MINUTES));
        }
    }

    /**
     * Registra um sucesso do ViaCEP (reset contador de falhas)
     */
    private void recordSuccess() {
        int previousFailures = consecutiveFailures.get();
        if (previousFailures > 0) {
            consecutiveFailures.set(0);
            lastFailureTime.set(null);
            System.out.println("ViaCEP: Serviço restaurado após falhas anteriores");
        }
    }

}
