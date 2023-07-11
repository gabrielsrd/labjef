package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncaoTecnicaDocenteRowMapper implements RowMapper<FuncaoTecnicaDocente> {

    @Override
    public FuncaoTecnicaDocente mapRow(ResultSet rs, int rowNum) throws SQLException {
        FuncaoTecnicaDocente funcaoTecnicaDocente = new FuncaoTecnicaDocente();
        funcaoTecnicaDocente.setId(rs.getInt("id"));
        funcaoTecnicaDocente.setFuncTec(rs.getString("func_tec"));
        funcaoTecnicaDocente.setIdDocente(rs.getInt("id_docente"));
        return funcaoTecnicaDocente;
    }
}
