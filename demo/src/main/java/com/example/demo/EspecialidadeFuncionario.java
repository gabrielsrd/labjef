package com.example.demo;

public class EspecialidadeFuncionario {
    private Integer id;
    private String especialidade;
    private Integer idFuncionario;

    public EspecialidadeFuncionario() {
        this.id = -1;
    }

    public EspecialidadeFuncionario(Integer id, String especialidade, Integer idFuncionario) {
        this.id = id;
        this.especialidade = especialidade;
        this.idFuncionario = idFuncionario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
