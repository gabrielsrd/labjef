package com.example.demo;

public class FuncaoTecnicaDocente {
    private Integer id;
    private String funcTec;
    private Integer idDocente;

    public FuncaoTecnicaDocente() {
        this.id = -1;
    }

    public FuncaoTecnicaDocente(Integer id, String funcTec, Integer idDocente) {
        this.id = id;
        this.funcTec = funcTec;
        this.idDocente = idDocente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuncTec() {
        return funcTec;
    }

    public void setFuncTec(String funcTec) {
        this.funcTec = funcTec;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }
}
