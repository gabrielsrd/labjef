package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocenteRowMapper implements RowMapper<Docente> {

    @Override
    public Docente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Docente docente = new Docente();
        docente.setId(rs.getInt("id"));
        docente.setIdUsuario(rs.getInt("id_usuario"));
        return docente;
    }
}
