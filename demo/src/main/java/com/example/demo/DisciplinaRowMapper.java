package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DisciplinaRowMapper implements RowMapper<Disciplina> {

    @Override
    public Disciplina mapRow(ResultSet rs, int rowNum) throws SQLException {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(rs.getInt("id"));
        disciplina.setCodigo(rs.getInt("codigo"));
        disciplina.setEmenta(rs.getString("ementa"));
        disciplina.setDataCriacao(rs.getDate("data_criacao"));
        disciplina.setNome(rs.getString("nome"));
        return disciplina;
    }
}
