package br.edu.infnet.heitorapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class TipoDelito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome do tipo de delito é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "A categoria é obrigatória.")
    @Size(min = 3, max = 50, message = "A categoria deve ter entre 3 e 50 caracteres.")
    private String categoria;

    @NotNull(message = "A gravidade média é obrigatória.")
    @Min(value = 1, message = "A gravidade média deve estar entre 1 e 10.")
    @Max(value = 10, message = "A gravidade média deve estar entre 1 e 10.")
    private int gravidadeMedia;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String descricao;

    private boolean ativo = true;

    @Override
    public String toString() {
        return String.format("TipoDelito{id=%d, nome='%s', categoria='%s', gravidadeMedia=%d, ativo=%s}", 
                id, nome, categoria, gravidadeMedia, ativo ? "sim" : "não");
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getGravidadeMedia() {
        return gravidadeMedia;
    }

    public void setGravidadeMedia(int gravidadeMedia) {
        this.gravidadeMedia = gravidadeMedia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
