package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelUsuarioPerfilRowMapper implements RowMapper<RelUsuarioPerfil> {

    @Override
    public RelUsuarioPerfil mapRow(ResultSet rs, int rowNum) throws SQLException {
        RelUsuarioPerfil relUsuarioPerfil = new RelUsuarioPerfil();
        relUsuarioPerfil.setId(rs.getInt("id"));
        relUsuarioPerfil.setIdPerfil(rs.getInt("id_perf"));
        relUsuarioPerfil.setIdUsuario(rs.getInt("id_usuario"));
        return relUsuarioPerfil;
    }
}
