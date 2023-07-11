package com.example.demo;

public class FuncaoTecnicaFuncionario {
    private Integer id;
    private String funcTec;
    private Integer idFuncionario;

    public FuncaoTecnicaFuncionario() {
        this.id = -1;
    }

    public FuncaoTecnicaFuncionario(Integer id, String funcTec, Integer idFuncionario) {
        this.id = id;
        this.funcTec = funcTec;
        this.idFuncionario = idFuncionario;
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

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
