package com.example.demo;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Timestamp;

@Controller
public class RelUsuarioServicoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/rel-usuarios-servicos")
    public String listaRelUsuariosServicos(Model model) {
        List<RelUsuarioServico> relUsuariosServicos = jdbcTemplate.query(
                "SELECT * FROM rel_usuario_servico ORDER BY id DESC",
                new RelUsuarioServicoRowMapper());
        model.addAttribute("listaRelUsuariosServicos", relUsuariosServicos);
        return "rel-usuarios-servicos";
    }

    @GetMapping("/rel-usuario-servico")
    public String formRelUsuarioServico(Model model) {
        model.addAttribute("relUsuarioServico", new RelUsuarioServico());
        return "rel-usuario-servico";
    }

    @GetMapping("/rel-usuario-servico/{id}")
    public String editRelUsuarioServico(Model model, @PathVariable Integer id) {
        RelUsuarioServico relUsuarioServico = jdbcTemplate.queryForObject("SELECT * FROM rel_usuario_servico WHERE id = ?",
                new RelUsuarioServicoRowMapper(), id);
        model.addAttribute("relUsuarioServico", relUsuarioServico);
        return "rel-usuario-servico";
    }

    @PostMapping("/rel-usuario-servico")
    public String submitRelUsuarioServico(@ModelAttribute RelUsuarioServico relUsuarioServico, Model model) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        relUsuarioServico.setDateTime(currentDate);
        if (relUsuarioServico.getId() != null && relUsuarioServico.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE rel_usuario_servico SET id_servico = ?, id_usuario = ?, data_time = ? WHERE id = ?",
                    relUsuarioServico.getIdServico(),
                    relUsuarioServico.getIdUsuario(),
                    relUsuarioServico.getDateTime(),
                    relUsuarioServico.getId());
        } else {
            String sql = "INSERT INTO rel_usuario_servico (id_servico, id_usuario, data_time) VALUES (?, ?, ?)";
            Object[] values = {
                    relUsuarioServico.getIdServico(),
                    relUsuarioServico.getIdUsuario(),
                    relUsuarioServico.getDateTime()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/rel-usuarios-servicos";
    }

    @DeleteMapping("/rel-usuario-servico/{id}")
    public String deleteRelUsuarioServico(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM rel_usuario_servico WHERE id = ?",
                id);
        return "redirect:/rel-usuarios-servicos";
    }
}
