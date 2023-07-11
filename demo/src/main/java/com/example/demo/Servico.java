package com.example.demo;

public class Servico {

    private Integer id;
    private String descricao;
    private String tipoServ;
    private Integer codServ;
    private Integer idPerfil;

    public Servico() {
        this.id = -1;
    }

    public Servico(Integer id, String descricao, String tipoServ, Integer codServ, Integer idPerfil) {
        this.id = id;
        this.descricao = descricao;
        this.tipoServ = tipoServ;
        this.codServ = codServ;
        this.idPerfil = idPerfil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoServ() {
        return tipoServ;
    }

    public void setTipoServ(String tipoServ) {
        this.tipoServ = tipoServ;
    }

    public Integer getCodServ() {
        return codServ;
    }

    public void setCodServ(Integer codServ) {
        this.codServ = codServ;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }
}
