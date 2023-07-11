package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EspecialidadeFuncionarioRowMapper implements RowMapper<EspecialidadeFuncionario> {

    @Override
    public EspecialidadeFuncionario mapRow(ResultSet rs, int rowNum) throws SQLException {
        EspecialidadeFuncionario especialidadeFuncionario = new EspecialidadeFuncionario();
        especialidadeFuncionario.setId(rs.getInt("id"));
        especialidadeFuncionario.setEspecialidade(rs.getString("especialidade"));
        especialidadeFuncionario.setIdFuncionario(rs.getInt("id_func"));
        return especialidadeFuncionario;
    }
}
