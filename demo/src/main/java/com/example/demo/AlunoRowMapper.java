package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoRowMapper implements RowMapper<Aluno> {

    @Override
    public Aluno mapRow(ResultSet rs, int rowNum) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id"));
        aluno.setIdUsuario(rs.getInt("id_usuario"));
        return aluno;
    }
}
