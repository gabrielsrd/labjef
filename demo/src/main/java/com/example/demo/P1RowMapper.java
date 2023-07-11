package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class P1RowMapper implements RowMapper<Perfil> {

    @Override
    public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
        Perfil perfil = new Perfil();
        perfil.setId(rs.getInt("id"));
        perfil.setTipoPerf(rs.getString("tipo_perf"));
        perfil.setCodPerf(rs.getString("cod_perf"));
        return perfil;
    }
}
