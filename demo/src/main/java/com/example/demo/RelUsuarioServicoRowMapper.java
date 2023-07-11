package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelUsuarioServicoRowMapper implements RowMapper<RelUsuarioServico> {

    @Override
    public RelUsuarioServico mapRow(ResultSet rs, int rowNum) throws SQLException {
        RelUsuarioServico relUsuarioServico = new RelUsuarioServico();
        relUsuarioServico.setId(rs.getInt("id"));
        relUsuarioServico.setIdServico(rs.getInt("id_servico"));
        relUsuarioServico.setIdUsuario(rs.getInt("id_usuario"));
        relUsuarioServico.setDateTime(rs.getTimestamp("data_time"));
        return relUsuarioServico;
    }
}
