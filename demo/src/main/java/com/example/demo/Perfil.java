package com.example.demo;

public class Perfil {
  
    private Integer id;
    private String tipoPerf;
    private Integer codPerf;

    public Perfil() {
        this.id = -1;
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoPerf() {
        return tipoPerf;
    }

    public void setTipoPerf(String tipoPerf) {
        this.tipoPerf = tipoPerf;
    }

    public Integer getCodPerf() {
        return codPerf;
    }

    public void setCodPerf(Integer codPerf) {
        this.codPerf = codPerf;
    }
}
