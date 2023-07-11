package com.example.demo;

import java.sql.Date;

public class DisciplinaAlunoDocente {
    private Integer id;
    private Integer idAluno;
    private Integer idDocente;
    private Integer idDisciplina;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer nota;

    public DisciplinaAlunoDocente() {
        this.id = -1;
    }

    public DisciplinaAlunoDocente(Integer id, Integer idAluno, Integer idDocente, Integer idDisciplina, Date dataInicio, Date dataFim, Integer nota) {
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
