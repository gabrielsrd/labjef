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
public class RelAlunoCursoController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/relAlunoCursos")
    public String listRelAlunoCursos(Model model) {
        List<RelAlunoCurso> relAlunoCursos = jdbcTemplate.query(
                "SELECT * FROM rel_aluno_curso",
                new RelAlunoCursoRowMapper());
        model.addAttribute("relAlunoCursos", relAlunoCursos);
        return "relAlunoCursos";
    }

    @GetMapping("/relAlunoCurso")
    public String formRelAlunoCurso(Model model) {
        model.addAttribute("relAlunoCurso", new RelAlunoCurso());
        return "relAlunoCurso";
    }

    @GetMapping("/relAlunoCurso/{id}")
    public String editRelAlunoCurso(Model model, @PathVariable Integer id) {
        RelAlunoCurso relAlunoCurso = jdbcTemplate.queryForObject("SELECT * FROM rel_aluno_curso WHERE id = ?",
                new RelAlunoCursoRowMapper(), id);
        model.addAttribute("relAlunoCurso", relAlunoCurso);
        return "relAlunoCurso";
    }

@   PostMapping("/relAlunoCurso")
    public String submitRelAlunoCurso(@ModelAttribute RelAlunoCurso relAlunoCurso, Model model) {
        if (relAlunoCurso.getId() != null && relAlunoCurso.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE rel_aluno_curso SET data_inic = ?, data_fim = ?, nota_ingresso = ?, id_curso = ?, id_aluno = ? WHERE id = ?",
                    relAlunoCurso.getDataInicio(),
                    relAlunoCurso.getDataFim(),
                    relAlunoCurso.getNotaIngresso(),
                    relAlunoCurso.getIdCurso(),
                    relAlunoCurso.getIdAluno(),
                    relAlunoCurso.getId());
        } else {
            String sql = "INSERT INTO rel_aluno_curso (data_inic, data_fim, nota_ingresso, id_curso, id_aluno) VALUES (?, ?, ?, ?, ?)";
            Object[] values = {
                    relAlunoCurso.getDataInicio(),
                    relAlunoCurso.getDataFim(),
                    relAlunoCurso.getNotaIngresso(),
                    relAlunoCurso.getIdCurso(),
                    relAlunoCurso.getIdAluno(),
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/relAlunoCursos";
    }

    @DeleteMapping("/relAlunoCurso/{id}")
    public String deleteRelAlunoCurso(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM rel_aluno_curso WHERE id = ?",
                id);
        return "redirect:/relAlunoCursos";
    }
}
