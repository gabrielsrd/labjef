package com.example.demo;

public class EspecialidadeDocente {
    private Integer id;
    private String especialidade;
    private Integer idDocente;

    public EspecialidadeDocente() {
        this.id = -1;
    }

    public EspecialidadeDocente(Integer id) {
        this.id = id;
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

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }
}
