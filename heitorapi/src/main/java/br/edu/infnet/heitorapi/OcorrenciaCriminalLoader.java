package br.edu.infnet.heitorapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;


import br.edu.infnet.heitorapi.model.domain.Endereco;
import br.edu.infnet.heitorapi.model.domain.OcorrenciaCriminal;
import br.edu.infnet.heitorapi.model.domain.TipoDelito;
import br.edu.infnet.heitorapi.model.domain.Vitima;

import br.edu.infnet.heitorapi.model.service.OcorrenciaCriminalService;
import jakarta.transaction.Transactional;

@Component
public class OcorrenciaCriminalLoader implements ApplicationRunner {
    
    private final OcorrenciaCriminalService ocorrenciaService;
    
    public OcorrenciaCriminalLoader(OcorrenciaCriminalService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        
        FileReader arquivo = new FileReader("ocorrencias.txt");
        BufferedReader leitura = new BufferedReader(arquivo);
        
        String linha = leitura.readLine();
        String[] campos = null;


        
        while(linha != null) {
            

            if (linha.trim().isEmpty()) {
                linha = leitura.readLine();
                continue;
            }
            
            campos = linha.split(";");
            

            if (campos.length < 12) {
                linha = leitura.readLine();
                continue;
            }
            

            

            Endereco endereco = new Endereco();
            endereco.setCep(campos[6]);
            endereco.setLogradouro(campos[7]);
            endereco.setBairro(campos[8]);
            endereco.setCidade(campos[9]);
            endereco.setUf(campos[10]);
            



            TipoDelito tipoDelito = new TipoDelito();
            tipoDelito.setNome(campos[1]);
            tipoDelito.setCategoria("Geral");
            tipoDelito.setGravidadeMedia(5);
            tipoDelito.setDescricao("Tipo automático");
            tipoDelito.setAtivo(true);
            

            OcorrenciaCriminal ocorrencia = new OcorrenciaCriminal();
            ocorrencia.setNumeroProtocolo(Integer.valueOf(campos[0]));
            ocorrencia.setTipoDelito(tipoDelito);
            ocorrencia.setDescricao(campos[2]);
            ocorrencia.setResolvida(Boolean.valueOf(campos[3]));
            ocorrencia.setGravidade(Integer.valueOf(campos[4]));
            ocorrencia.setDataOcorrencia(LocalDateTime.parse(campos[5]));
            ocorrencia.setEndereco(endereco);
            

            Vitima vitima = new Vitima();
            vitima.setNome("Vítima " + campos[0]);
            vitima.setCpf("000.000.000-00");
            vitima.setEmail("vitima@email.com");
            vitima.setTelefone("(00)0000-0000");
            vitima.setIdade(30);
            vitima.setTemDefensor(false);
            vitima.setProfissao("Cidadão");
            vitima.setOcorrencia(ocorrencia);
            
            ocorrencia.setVitimas(Arrays.asList(vitima));
            
            try {
                ocorrenciaService.incluir(ocorrencia);

            } catch (Exception e) {
                System.err.println("  [ERRO] Problema na inclusão da ocorrência " + campos[0] + ": " + e.getMessage());
            }

            linha = leitura.readLine();
        }
        

        

        
        leitura.close();
    }
}
