package com.example.demo;

public class Curso {
    private Integer id;
    private Integer codCurso;

    public Curso() {
        this.id = -1;
    }

    public Curso(Integer id, Integer codCurso) {
        this.id = id;
        this.codCurso = codCurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(Integer codCurso) {
        this.codCurso = codCurso;
    }
}
