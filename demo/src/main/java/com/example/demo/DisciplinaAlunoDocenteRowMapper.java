package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DisciplinaAlunoDocenteRowMapper implements RowMapper<DisciplinaAlunoDocente> {

    @Override
    public DisciplinaAlunoDocente mapRow(ResultSet rs, int rowNum) throws SQLException {
        DisciplinaAlunoDocente disciplinaAlunoDocente = new DisciplinaAlunoDocente();
        disciplinaAlunoDocente.setId(rs.getInt("id"));
        disciplinaAlunoDocente.setIdAluno(rs.getInt("id_aluno"));
        disciplinaAlunoDocente.setIdDocente(rs.getInt("id_docente"));
        disciplinaAlunoDocente.setIdDisciplina(rs.getInt("id_disc"));
        disciplinaAlunoDocente.setDataInicio(rs.getDate("data_inic").toLocalDate());
        if (rs.getDate("data_fim") != null) {
            disciplinaAlunoDocente.setDataFim(rs.getDate("data_fim").toLocalDate());
        }
        if (rs.getObject("nota") != null) {
            disciplinaAlunoDocente.setNota(rs.getInt("nota"));
        }
        return disciplinaAlunoDocente;
    }
}
