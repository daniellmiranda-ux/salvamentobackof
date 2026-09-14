package com.example.backhelp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_chamados")
public class ChamadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "protocolo", nullable = false, unique = true, updatable = false)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgencia urgencia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    private String caminhoAnexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.ABERTO;

    @Enumerated(EnumType.STRING)
    private Perfil nivelAtendimento;

    @Column(updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataFinalizacao;
    private LocalDateTime dataLimiteSla;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioAbertura;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private UsuarioModel atendenteResponsavel;

    @Column(columnDefinition = "TEXT")
    private String solucao;



    public ChamadoModel() {
    }

    public ChamadoModel(Long id, String protocolo, Categoria categoria, Urgencia urgencia, String descricao,
                        String caminhoAnexo, StatusChamado status, Perfil nivelAtendimento,
                        LocalDateTime dataCriacao, LocalDateTime dataFinalizacao, LocalDateTime dataLimiteSla,
                        UsuarioModel usuarioAbertura, UsuarioModel atendenteResponsavel, String solucao) {
        this.id = id;
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.urgencia = urgencia;
        this.descricao = descricao;
        this.caminhoAnexo = caminhoAnexo;
        this.status = status;
        this.nivelAtendimento = nivelAtendimento;
        this.dataCriacao = dataCriacao;
        this.dataFinalizacao = dataFinalizacao;
        this.dataLimiteSla = dataLimiteSla;
        this.usuarioAbertura = usuarioAbertura;
        this.atendenteResponsavel = atendenteResponsavel;
        this.solucao = solucao;
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();

        if (this.protocolo == null) {
            this.protocolo = "HD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        calcularSla();
    }

    @PreUpdate
    public void preUpdate() {
        calcularSla();
    }

    private void calcularSla() {
        if (this.urgencia != null && this.dataCriacao != null) {
            this.dataLimiteSla = this.dataCriacao.plusHours(this.urgencia.getHorasSla());
        }
    }

    public boolean isAtrasado() {
        if (this.status == StatusChamado.FECHADO) {
            return false;
        }
        return this.dataLimiteSla != null && LocalDateTime.now().isAfter(this.dataLimiteSla);
    }

    public Long getId() { return id; }
    public String getProtocolo() { return protocolo; }
    public Categoria getCategoria() { return categoria; }
    public Urgencia getUrgencia() { return urgencia; }
    public String getDescricao() { return descricao; }
    public String getCaminhoAnexo() { return caminhoAnexo; }
    public StatusChamado getStatus() { return status; }
    public Perfil getNivelAtendimento() { return nivelAtendimento; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataFinalizacao() { return dataFinalizacao; }
    public LocalDateTime getDataLimiteSla() { return dataLimiteSla; }
    public UsuarioModel getUsuarioAbertura() { return usuarioAbertura; }
    public UsuarioModel getAtendenteResponsavel() { return atendenteResponsavel; }
    public String getSolucao() { return solucao; }

    public void setId(Long id) { this.id = id; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setUrgencia(Urgencia urgencia) { this.urgencia = urgencia; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCaminhoAnexo(String caminhoAnexo) { this.caminhoAnexo = caminhoAnexo; }
    public void setStatus(StatusChamado status) { this.status = status; }
    public void setNivelAtendimento(Perfil nivelAtendimento) { this.nivelAtendimento = nivelAtendimento; }
    public void setDataFinalizacao(LocalDateTime dataFinalizacao) { this.dataFinalizacao = dataFinalizacao; }
    public void setDataLimiteSla(LocalDateTime dataLimiteSla) { this.dataLimiteSla = dataLimiteSla; }
    public void setUsuarioAbertura(UsuarioModel usuarioAbertura) { this.usuarioAbertura = usuarioAbertura; }
    public void setAtendenteResponsavel(UsuarioModel atendenteResponsavel) { this.atendenteResponsavel = atendenteResponsavel; }
    public void setSolucao(String solucao) { this.solucao = solucao; }
}