package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelAlunoCursoRowMapper implements RowMapper<RelAlunoCurso> {

    @Override
    public RelAlunoCurso mapRow(ResultSet rs, int rowNum) throws SQLException {
        RelAlunoCurso relAlunoCurso = new RelAlunoCurso();
        relAlunoCurso.setId(rs.getInt("id"));
        relAlunoCurso.setDataInicio(rs.getDate("data_inic"));
        relAlunoCurso.setDataFim(rs.getString("data_fim"));
        relAlunoCurso.setNotaIngresso(rs.getFloat("nota_ingresso"));
        relAlunoCurso.setIdCurso(rs.getInt("id_curso"));
        relAlunoCurso.setIdAluno(rs.getInt("id_aluno"));
        return relAlunoCurso;
    }
}
