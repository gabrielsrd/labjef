package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoRowMapper implements RowMapper<Curso> {

    @Override
    public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {
        Curso curso = new Curso();
        curso.setId(rs.getInt("id"));
        curso.setCodCurso(rs.getString("cod_curso"));
        return curso;
    }
}
