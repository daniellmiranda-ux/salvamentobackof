package com.example.backhelp.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String setor;
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private boolean emailConfirmado = false;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioAbertura")
    private List<ChamadoModel> chamadosAbertos;

    @JsonIgnore
    @OneToMany(mappedBy = "atendenteResponsavel")
    private List<ChamadoModel> chamadosAtendidos;

    public UsuarioModel() {
    }

    public UsuarioModel(Long id, String email, String senha, String setor, String cargo, Perfil perfil, boolean emailConfirmado) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.cargo = cargo;
        this.perfil = perfil;
        this.emailConfirmado = emailConfirmado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isEmailConfirmado() {
        return emailConfirmado;
    }

    public void setEmailConfirmado(boolean emailConfirmado) {
        this.emailConfirmado = emailConfirmado;
    }
    public List<ChamadoModel> getChamadosAbertos() {
        return chamadosAbertos;
    }

    public void setChamadosAbertos(List<ChamadoModel> chamadosAbertos) {
        this.chamadosAbertos = chamadosAbertos;
    }

    public List<ChamadoModel> getChamadosAtendidos() {
        return chamadosAtendidos;
    }

    public void setChamadosAtendidos(List<ChamadoModel> chamadosAtendidos) {
        this.chamadosAtendidos = chamadosAtendidos;
    }
}