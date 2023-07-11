package com.example.demo;

public class Curso {
    private Integer id;
    private String codCurso;

    public Curso() {
        this.id = -1;
    }

    public Curso(Integer id, String codCurso) {
        this.id = id;
        this.codCurso = codCurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(String codCurso) {
        this.codCurso = codCurso;
    }
}
