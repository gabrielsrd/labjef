package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/alunos")
    public String listAlunos(Model model) {
        List<Aluno> alunos = jdbcTemplate.query(
                "SELECT * FROM aluno",
                new AlunoRowMapper()
        );
        model.addAttribute("alunos", alunos);
        return "alunos";
    }

    @GetMapping("/aluno")
    public String showAlunoForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno";
    }

    @PostMapping("/aluno")
    public String saveAluno(@ModelAttribute Aluno aluno) {
        jdbcTemplate.update(
                "INSERT INTO aluno (id_usuario) VALUES (?)",
                aluno.getIdUsuario()
        );
        return "redirect:/alunos";
    }

    @GetMapping("/aluno/{id}")
    public String editAlunoForm(@PathVariable("id") Integer id, Model model) {
        Aluno aluno = jdbcTemplate.queryForObject(
                "SELECT * FROM aluno WHERE id = ?",
                new AlunoRowMapper(),
                id
        );
        model.addAttribute("aluno", aluno);
        return "aluno";
    }

    @PostMapping("/aluno/{id}")
    public String updateAluno(@PathVariable("id") Integer id, @ModelAttribute Aluno aluno) {
        jdbcTemplate.update(
                "UPDATE aluno SET id_usuario = ? WHERE id = ?",
                aluno.getIdUsuario(),
                id
        );
        return "redirect:/alunos";
    }

    @GetMapping("/aluno/delete/{id}")
    public String deleteAluno(@PathVariable("id") Integer id) {
        jdbcTemplate.update(
                "DELETE FROM aluno WHERE id = ?",
                id
        );
        return "redirect:/alunos";
    }
}
