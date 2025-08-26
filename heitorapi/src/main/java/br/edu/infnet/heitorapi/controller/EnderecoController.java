package br.edu.infnet.heitorapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.heitorapi.model.domain.Endereco;
import br.edu.infnet.heitorapi.model.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "Consulta de endereços via ViaCEP")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/cep/{cep}")
    @Operation(summary = "Buscar endereço por CEP")
    public ResponseEntity<Endereco> buscarPorCep(@PathVariable String cep) {
        try {
            Endereco endereco = enderecoService.buscarEnderecoPorCep(cep);
            return ResponseEntity.ok(endereco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("indisponível temporariamente")) {
                return ResponseEntity.status(503).build();
            }
            return ResponseEntity.notFound().build();
        }
    }
}
