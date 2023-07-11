package com.example.demo;

public class RelDisciplinaCurso {

    private Integer id;
    private Integer idDisciplina;
    private Integer idCurso;

    public RelDisciplinaCurso() {
        this.id = -1;
    }

    public RelDisciplinaCurso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }
}
