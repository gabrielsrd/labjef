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
public class DisciplinaController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/disciplinas")
    public String listaDisciplinas(Model model) {
        List<Disciplina> disciplinas = jdbcTemplate.query(
                "SELECT * FROM disciplina ORDER BY id DESC",
                new DisciplinaRowMapper());
        model.addAttribute("listaDisciplinas", disciplinas);
        return "disciplinas";
    }

    @GetMapping("/disciplina")
    public String formDisciplina(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina";
    }

    @GetMapping("/disciplina/{id}")
    public String editDisciplina(Model model, @PathVariable Integer id) {
        Disciplina disciplina = jdbcTemplate.queryForObject("SELECT * FROM disciplina WHERE id = ?",
                new DisciplinaRowMapper(), id);
        model.addAttribute("disciplina", disciplina);
        return "disciplina";
    }

    @PostMapping("/disciplina")
    public String submitDisciplina(@ModelAttribute Disciplina disciplina, Model model) {
        if (disciplina.getId() != null && disciplina.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE disciplina SET codigo = ?, ementa = ?, data_criacao = ?, nome = ? WHERE id = ?",
                    disciplina.getCodigo(),
                    disciplina.getEmenta(),
                    disciplina.getDataCriacao(),
                    disciplina.getNome(),
                    disciplina.getId());
        } else {
            String sql = "INSERT INTO disciplina (codigo, ementa, data_criacao, nome) VALUES (?, ?, ?, ?)";
            Object[] values = {
                    disciplina.getCodigo(),
                    disciplina.getEmenta(),
                    disciplina.getDataCriacao(),
                    disciplina.getNome()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/disciplinas";
    }

    @DeleteMapping("/disciplina/{id}")
    public String deleteDisciplina(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM disciplina WHERE id = ?",
                id);
        return "redirect:/disciplinas";
    }
}
