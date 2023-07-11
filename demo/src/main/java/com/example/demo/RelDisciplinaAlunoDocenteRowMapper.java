package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelDisciplinaAlunoDocenteRowMapper implements RowMapper<RelDisciplinaAlunoDocente> {

    @Override
    public RelDisciplinaAlunoDocente mapRow(ResultSet rs, int rowNum) throws SQLException {
        RelDisciplinaAlunoDocente relDisciplinaAlunoDocente = new RelDisciplinaAlunoDocente();
        relDisciplinaAlunoDocente.setId(rs.getInt("id"));
        relDisciplinaAlunoDocente.setIdAluno(rs.getInt("id_aluno"));
        relDisciplinaAlunoDocente.setIdDocente(rs.getInt("id_docente"));
        relDisciplinaAlunoDocente.setIdDisciplina(rs.getInt("id_disc"));
        relDisciplinaAlunoDocente.setDataInicio(rs.getDate("data_inic"));
        relDisciplinaAlunoDocente.setDataFim(rs.getString("data_fim"));
        relDisciplinaAlunoDocente.setNota(rs.getString("nota"));
        return relDisciplinaAlunoDocente;
    }
}
