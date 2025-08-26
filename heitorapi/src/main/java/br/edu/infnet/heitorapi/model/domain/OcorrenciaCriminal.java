package br.edu.infnet.heitorapi.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class OcorrenciaCriminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "O número do protocolo é obrigatório.")
    @Min(value = 1, message = "O número do protocolo deve ser um número positivo.")
    private int numeroProtocolo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_delito_id")
    @NotNull(message = "O tipo de delito é obrigatório.")
    @Valid
    private TipoDelito tipoDelito;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres.")
    private String descricao;

    private boolean resolvida;

    @NotNull(message = "A gravidade é obrigatória.")
    @Min(value = 1, message = "A gravidade deve estar entre 1 e 10.")
    @Max(value = 10, message = "A gravidade deve estar entre 1 e 10.")
    private int gravidade;

    @NotNull(message = "A data da ocorrência é obrigatória.")
    private LocalDateTime dataOcorrencia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @Valid
    private Endereco endereco;

    @OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vitima> vitimas;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("OcorrenciaCriminal{id=%d, protocolo=%d, tipoDelito='%s', descricao='%s', " +
                "resolvida=%s, gravidade=%d, dataOcorrencia=%s",
                id, numeroProtocolo,
                tipoDelito != null ? tipoDelito.getNome() : "N/A", descricao,
                resolvida ? "sim" : "não", gravidade,
                dataOcorrencia != null ? dataOcorrencia.format(formatter) : "N/A"));

        if (endereco != null) {
            sb.append(", endereco={").append(endereco.toString()).append("}");
        }



        if (vitimas != null && !vitimas.isEmpty()) {
            sb.append(", vitimas=[");
            for (int i = 0; i < vitimas.size(); i++) {
                sb.append(vitimas.get(i).getNome());
                if (i < vitimas.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        }

        sb.append("}");
        return sb.toString();
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(int numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public TipoDelito getTipoDelito() {
        return tipoDelito;
    }

    public void setTipoDelito(TipoDelito tipoDelito) {
        this.tipoDelito = tipoDelito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isResolvida() {
        return resolvida;
    }

    public void setResolvida(boolean resolvida) {
        this.resolvida = resolvida;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }



    public List<Vitima> getVitimas() {
        return vitimas;
    }

    public void setVitimas(List<Vitima> vitimas) {
        this.vitimas = vitimas;
    }
}

