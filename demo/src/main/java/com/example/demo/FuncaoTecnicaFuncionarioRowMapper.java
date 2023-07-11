package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncaoTecnicaFuncionarioRowMapper implements RowMapper<FuncaoTecnicaFuncionario> {

    @Override
    public FuncaoTecnicaFuncionario mapRow(ResultSet rs, int rowNum) throws SQLException {
        FuncaoTecnicaFuncionario funcaoTecnicaFuncionario = new FuncaoTecnicaFuncionario();
        funcaoTecnicaFuncionario.setId(rs.getInt("id"));
        funcaoTecnicaFuncionario.setFuncTec(rs.getString("func_tec"));
        funcaoTecnicaFuncionario.setIdFuncionario(rs.getInt("id_func"));
        return funcaoTecnicaFuncionario;
    }
}
