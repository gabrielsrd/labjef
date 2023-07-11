package com.example.demo;

public class RelUsuarioPerfil {

    private Integer id;
    private Integer idPerfil;
    private Integer idUsuario;

    public RelUsuarioPerfil() {
        this.id = -1;
    }

    public RelUsuarioPerfil(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
