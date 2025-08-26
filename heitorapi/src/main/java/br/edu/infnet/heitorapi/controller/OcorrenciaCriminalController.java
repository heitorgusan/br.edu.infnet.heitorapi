package br.edu.infnet.heitorapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.heitorapi.model.domain.OcorrenciaCriminal;
import br.edu.infnet.heitorapi.model.service.OcorrenciaCriminalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ocorrencias")
@Tag(name = "Ocorrências Criminais", description = "CRUD de ocorrências criminais")
public class OcorrenciaCriminalController {

    private final OcorrenciaCriminalService ocorrenciaService;
    
    public OcorrenciaCriminalController(OcorrenciaCriminalService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }
    
    @PostMapping
    @Operation(summary = "Criar ocorrência")
    public ResponseEntity<OcorrenciaCriminal> incluir(@Valid @RequestBody OcorrenciaCriminal ocorrencia) {
        OcorrenciaCriminal novaOcorrencia = ocorrenciaService.incluir(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOcorrencia);
    }
    
    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar ocorrência")
    public ResponseEntity<OcorrenciaCriminal> alterar(@PathVariable Integer id, @Valid @RequestBody OcorrenciaCriminal ocorrencia) {
        OcorrenciaCriminal ocorrenciaAlterada = ocorrenciaService.alterar(id, ocorrencia);
        return ResponseEntity.ok(ocorrenciaAlterada);
    }
    
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir ocorrência")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        ocorrenciaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping
    @Operation(summary = "Listar ocorrências")
    public ResponseEntity<List<OcorrenciaCriminal>> obterLista() {
        List<OcorrenciaCriminal> lista = ocorrenciaService.obterLista();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar ocorrência por ID")
    public ResponseEntity<OcorrenciaCriminal> obterPorId(@PathVariable Integer id) {
        OcorrenciaCriminal ocorrencia = ocorrenciaService.obterPorId(id);
        return ResponseEntity.ok(ocorrencia);
    }
    

    @GetMapping("/cidade/{cidade}")
    @Operation(summary = "Buscar por cidade")
    public ResponseEntity<List<OcorrenciaCriminal>> obterPorCidade(@PathVariable String cidade) {
        List<OcorrenciaCriminal> ocorrencias = ocorrenciaService.obterPorCidade(cidade);
        
        if (ocorrencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(ocorrencias);
    }
}
