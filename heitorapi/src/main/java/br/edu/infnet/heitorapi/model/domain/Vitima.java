package br.edu.infnet.heitorapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

@Entity
public class Vitima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail está inválido.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotNull(message = "A idade é obrigatória.")
    @Min(value = 0, message = "A idade deve ser um número positivo.")
    @Max(value = 120, message = "A idade deve ser menor que 120 anos.")
    private int idade;
    
    private boolean temDefensor;
    
    @Size(max = 100, message = "A profissão deve ter até 100 caracteres.")
    private String profissao;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private OcorrenciaCriminal ocorrencia;

    @Override
    public String toString() {
        return String.format("Vitima{id=%d, nome=%s, idade=%d, temDefensor=%s, profissao='%s'}", 
                id, nome, idade, temDefensor ? "sim" : "não", profissao);
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isTemDefensor() {
        return temDefensor;
    }

    public void setTemDefensor(boolean temDefensor) {
        this.temDefensor = temDefensor;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public OcorrenciaCriminal getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(OcorrenciaCriminal ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
}

