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

@Controller
public class RelUsuarioPerfilController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/relUsuarioPerfis")
    public String listaRelUsuarioPerfil(Model model) {
        List<RelUsuarioPerfil> relUsuarioPerfilList = jdbcTemplate.query(
                "SELECT * FROM rel_usuario_perfil",
                new RelUsuarioPerfilRowMapper());
        model.addAttribute("relUsuarioPerfilList", relUsuarioPerfilList);
        return "relUsuarioPerfis";
    }

    @GetMapping("/relUsuarioPerfil")
    public String formDisciplinaCurso(Model model) {
        model.addAttribute("relUsuarioPerfil", new RelUsuarioPerfil());
        return "relUsuarioPerfil";
    }

    @GetMapping("/relUsuarioPerfil/{id}")
    public String editRelUsuarioPerfil(Model model, @PathVariable Integer id) {
        RelUsuarioPerfil relUsuarioPerfil = jdbcTemplate.queryForObject("SELECT * FROM rel_usuario_perfil WHERE id = ?",
                new RelUsuarioPerfilRowMapper(), id);
        model.addAttribute("relUsuarioPerfil", relUsuarioPerfil);
        return "relUsuarioPerfil";
    }

    @PostMapping("/relUsuarioPerfil")
    public String submitRelUsuarioPerfil(@ModelAttribute RelUsuarioPerfil relUsuarioPerfil, Model model) {
        if (relUsuarioPerfil.getId() != null && relUsuarioPerfil.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE rel_usuario_perfil SET id_perf = ?, id_usuario = ? WHERE id = ?",
                    relUsuarioPerfil.getIdPerfil(),
                    relUsuarioPerfil.getIdUsuario(),
                    relUsuarioPerfil.getId());
        } else {
            String sql = "INSERT INTO rel_usuario_perfil (id_perf, id_usuario) VALUES (?, ?)";
            Object[] values = {
                    relUsuarioPerfil.getIdPerfil(),
                    relUsuarioPerfil.getIdUsuario()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/relUsuarioPerfis";
    }

    @DeleteMapping("/relUsuarioPerfil/{id}")
    public String deleteRelUsuarioPerfil(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM rel_usuario_perfil WHERE id = ?",
                id);
        return "redirect:/relUsuarioPerfis";
    }
}
