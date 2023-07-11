package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EspecialidadeDocenteRowMapper implements RowMapper<EspecialidadeDocente> {

    @Override
    public EspecialidadeDocente mapRow(ResultSet rs, int rowNum) throws SQLException {
        EspecialidadeDocente especialidadeDocente = new EspecialidadeDocente();
        especialidadeDocente.setId(rs.getInt("id"));
        especialidadeDocente.setEspecialidade(rs.getString("especialidade"));
        especialidadeDocente.setIdDocente(rs.getInt("id_docente"));
        return especialidadeDocente;
    }
}
