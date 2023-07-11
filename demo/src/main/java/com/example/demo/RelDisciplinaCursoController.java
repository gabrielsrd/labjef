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
public class RelDisciplinaCursoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/relDisciplinaCursos")
    public String listaRelDisciplinaCurso(Model model) {
        List<RelDisciplinaCurso> relDisciplinaCursoList = jdbcTemplate.query(
                "SELECT * FROM rel_disciplina_curso",
                new RelDisciplinaCursoRowMapper());
        model.addAttribute("relDisciplinaCursoList", relDisciplinaCursoList);
        return "relDisciplinaCursos";
    }

    @GetMapping("/relDisciplinaCurso")
    public String formDisciplinaCurso(Model model) {
        model.addAttribute("relDisciplinaCurso", new RelDisciplinaCurso());
        return "relDisciplinaCurso";
    }

    @GetMapping("/relDisciplinaCurso/{id}")
    public String editRelDisciplinaCurso(Model model, @PathVariable Integer id) {
        RelDisciplinaCurso relDisciplinaCurso = jdbcTemplate.queryForObject("SELECT * FROM rel_disciplina_curso WHERE id = ?",
                new RelDisciplinaCursoRowMapper(), id);
        model.addAttribute("relDisciplinaCurso", relDisciplinaCurso);
        return "relDisciplinaCurso";
    }

    @PostMapping("/relDisciplinaCurso")
    public String submitRelDisciplinaCurso(@ModelAttribute RelDisciplinaCurso relDisciplinaCurso, Model model) {
        if (relDisciplinaCurso.getId() != null && relDisciplinaCurso.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE rel_disciplina_curso SET id_disc = ?, id_curso = ? WHERE id = ?",
                    relDisciplinaCurso.getIdDisciplina(),
                    relDisciplinaCurso.getIdCurso(),
                    relDisciplinaCurso.getId());
        } else {
            String sql = "INSERT INTO rel_disciplina_curso (id_disc, id_curso) VALUES (?, ?)";
            Object[] values = {
                    relDisciplinaCurso.getIdDisciplina(),
                    relDisciplinaCurso.getIdCurso()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/relDisciplinaCursos";
    }

    @DeleteMapping("/relDisciplinaCurso/{id}")
    public String deleteRelDisciplinaCurso(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM rel_disciplina_curso WHERE id = ?",
                id);
        return "redirect:/relDisciplinaCurso";
    }
}
