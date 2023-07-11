package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelDisciplinaCursoRowMapper implements RowMapper<RelDisciplinaCurso> {

    @Override
    public RelDisciplinaCurso mapRow(ResultSet rs, int rowNum) throws SQLException {
        RelDisciplinaCurso relDisciplinaCurso = new RelDisciplinaCurso();
        relDisciplinaCurso.setId(rs.getInt("id"));
        relDisciplinaCurso.setIdDisciplina(rs.getInt("id_disc"));
        relDisciplinaCurso.setIdCurso(rs.getInt("id_curso"));
        return relDisciplinaCurso;
    }
}
