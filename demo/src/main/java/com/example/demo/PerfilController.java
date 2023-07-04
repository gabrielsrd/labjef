package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class PerfilController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/perfis")
    public String listaPerfis(Model model) {
        List<Perfil> perfis = jdbcTemplate.query(
                "SELECT * FROM perfil ORDER BY id DESC",
                new PerfilRowMapper());
        model.addAttribute("listaPerfis", perfis);
        return "perfis";
    }

    @GetMapping("/perfil")
    public String formPerfil(Model model) {
        model.addAttribute("perfil", new Perfil());
        return "perfil";
    }

    @GetMapping("/perfil/{id}")
    public String editPerfil(Model model, @PathVariable Integer id) {
        Perfil perfil = jdbcTemplate.queryForObject("SELECT * FROM perfil WHERE id = ?",
                new PerfilRowMapper(), id);
        model.addAttribute("perfil", perfil);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String submitPerfil(@ModelAttribute Perfil perfil, Model model) {
        if (perfil.getId() != null && perfil.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE perfil SET tipo_perf = ?, cod_perf = ? WHERE id = ?",
                    perfil.getTipoPerf(),
                    perfil.getCodPerf(),
                    perfil.getId());
        } else {
            String sql = "INSERT INTO perfil (tipo_perf, cod_perf) VALUES (?, ?)";
            Object[] values = {
                    perfil.getTipoPerf(),
                    perfil.getCodPerf()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/perfis";
    }

    @DeleteMapping("/perfil/{id}")
    public String deletePerfil(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM perfil WHERE id = ?",
                id);
        return "redirect:/perfis";
    }
}
