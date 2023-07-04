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
public class CursoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/cursos")
    public String listCursos(Model model) {
        // Retrieve the list of cursos from the database
        List<Curso> cursos = jdbcTemplate.query(
                "SELECT * FROM curso",
                new CursoRowMapper());
        model.addAttribute("listaCursos", cursos);
        return "cursos"; // Assuming you have a cursos.html template
    }

    @GetMapping("/curso")
    public String formCurso(Model model) {
        model.addAttribute("curso", new Curso());
        return "curso"; // Assuming you have a curso.html template
    }

    @GetMapping("/curso/{id}")
    public String editCurso(Model model, @PathVariable Integer id) {
        Curso curso = jdbcTemplate.queryForObject("SELECT * FROM curso WHERE id = ?",
                new CursoRowMapper(), id);
        model.addAttribute("curso", curso);
        return "curso"; // Assuming you have a curso.html template
    }

    @PostMapping("/curso")
    public String submitCurso(@ModelAttribute Curso curso, Model model) {
        if (curso.getId() != null && curso.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE curso SET cod_curso = ? WHERE id = ?",
                    curso.getCodCurso(),
                    curso.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO curso (cod_curso) VALUES (?)",
                    curso.getCodCurso());
        }
        return "redirect:/cursos"; // Redirect to the cursos endpoint
    }

    @DeleteMapping("/curso/{id}")
    public String deleteCurso(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM curso WHERE id = ?",
                id);
        return "redirect:/cursos"; // Redirect to the cursos endpoint
    }
}
