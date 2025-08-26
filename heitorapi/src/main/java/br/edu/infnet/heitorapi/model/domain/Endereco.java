package br.edu.infnet.heitorapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Use o formato XXXXX-XXX.")
    private String cep; 

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(min = 3, max = 200, message = "Logradouro deve ter entre 3 e 200 caracteres.")
    private String logradouro; 

    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres.")
    private String bairro; 

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(min = 3, max = 100, message = "Cidade deve ter entre 3 e 100 caracteres.")
    private String cidade; 

    @NotBlank(message = "A UF é obrigatória.")
    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres.")
    private String uf; 

    @Override
    public String toString() {
         return String.format("Endereco{id=%d, cep='%s', logradouro='%s', complemento='%s', bairro='%s', cidade='%s', uf='%s'}",
                   id, cep, logradouro, complemento, bairro, cidade, uf);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}

