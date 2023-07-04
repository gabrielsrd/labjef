package com.example.demo;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoRowMapper implements RowMapper<Servico> {

    @Override
    public Servico mapRow(ResultSet rs, int rowNum) throws SQLException {
        Servico servico = new Servico();
        servico.setIdServ(rs.getInt("id_serv"));
        servico.setDescricao(rs.getString("descricao"));
        servico.setTipoServ(rs.getString("tipo_serv"));
        servico.setCodServ(rs.getInt("cod_serv"));
        servico.setIdPerfil(rs.getInt("id_perfil"));
        return servico;
    }
}