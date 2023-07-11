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
public class FuncionarioController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/funcionarios")
    public String listaFuncionarios(Model model) {
        List<Funcionario> funcionarios = jdbcTemplate.query(
                "SELECT * FROM funcionario",
                new FuncionarioRowMapper());
        model.addAttribute("listaFuncionarios", funcionarios);
        return "funcionarios";
    }

    @GetMapping("/funcionario")
    public String formFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionario";
    }

    @GetMapping("/funcionario/{id}")
    public String editFuncionario(Model model, @PathVariable Integer id) {
        Funcionario funcionario = jdbcTemplate.queryForObject("SELECT * FROM funcionario WHERE id = ?",
                new FuncionarioRowMapper(), id);
        model.addAttribute("funcionario", funcionario);
        return "funcionario";
    }

    @PostMapping("/funcionario")
    public String submitFuncionario(@ModelAttribute Funcionario funcionario, Model model) {
        if (funcionario.getId() != null && funcionario.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE funcionario SET id_usuario = ? WHERE id = ?",
                    funcionario.getIdUsuario(),
                    funcionario.getId());
        } else {
            String sql = "INSERT INTO funcionario (id_usuario) VALUES (?)";
            Object[] values = {
                    funcionario.getIdUsuario()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/funcionarios";
    }

    @DeleteMapping("/funcionario/{id}")
    public String deleteFuncionario(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM funcionario WHERE id = ?",
                id);
        return "redirect:/funcionarios";
    }
}
