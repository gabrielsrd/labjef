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
public class DocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/docentes")
    public String listaDocentes(Model model) {
        List<Docente> docentes = jdbcTemplate.query(
                "SELECT * FROM docente",
                new DocenteRowMapper());
        model.addAttribute("listaDocentes", docentes);
        return "docentes";
    }

    @GetMapping("/docente")
    public String formDocente(Model model) {
        model.addAttribute("docente", new Docente());
        return "docente";
    }

    @GetMapping("/docente/{id}")
    public String editDocente(Model model, @PathVariable Integer id) {
        Docente docente = jdbcTemplate.queryForObject("SELECT * FROM docente WHERE id = ?",
                new DocenteRowMapper(), id);
        model.addAttribute("docente", docente);
        return "docente";
    }

    @PostMapping("/docente")
    public String submitDocente(@ModelAttribute Docente docente, Model model) {
        if (docente.getId() != null && docente.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE docente SET id_usuario = ? WHERE id = ?",
                    docente.getIdUsuario(),
                    docente.getId());
        } else {
            String sql = "INSERT INTO docente (id_usuario) VALUES (?)";
            Object[] values = {
                    docente.getIdUsuario()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/docentes";
    }

    @DeleteMapping("/docente/{id}")
    public String deleteDocente(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM docente WHERE id = ?",
                id);
        return "redirect:/docentes";
    }
}