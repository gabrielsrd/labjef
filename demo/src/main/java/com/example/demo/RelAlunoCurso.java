package com.example.demo;

import java.sql.Date;

public class RelAlunoCurso {

    private Integer id;
    private Date dataInicio;
    private Date dataFim;
    private Integer notaIngresso;
    private Integer idCurso;
    private Integer idAluno;

    public RelAlunoCurso() {
        this.id = -1;
    }

    public RelAlunoCurso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getNotaIngresso() {
        return notaIngresso;
    }

    public void setNotaIngresso(Integer notaIngresso) {
        this.notaIngresso = notaIngresso;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }
}
