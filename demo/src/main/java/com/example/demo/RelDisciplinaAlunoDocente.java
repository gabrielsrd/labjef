package com.example.demo;

import java.sql.Date;

public class RelDisciplinaAlunoDocente {
    private Integer id;
    private Integer idAluno;
    private Integer idDocente;
    private Integer idDisciplina;
    private Date dataInicio;
    private String dataFim;
    private String nota;

    public RelDisciplinaAlunoDocente() {
        this.id = -1;
    }

    public RelDisciplinaAlunoDocente(Integer id, Integer idAluno, Integer idDocente, Integer idDisciplina, Date dataInicio, String dataFim, String nota) {
        this.id = id;
        this.idAluno = idAluno;
        this.idDocente = idDocente;
        this.idDisciplina = idDisciplina;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Integer idDocente) {
        this.idDocente = idDocente;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
