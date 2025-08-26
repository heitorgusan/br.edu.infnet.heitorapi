package br.edu.infnet.heitorapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.infnet.heitorapi.model.dto.ViaCepResponseDto;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponseDto buscarEnderecoPorCep(@PathVariable("cep") String cep);
}






