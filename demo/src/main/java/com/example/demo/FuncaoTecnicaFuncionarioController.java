package com.example.demo;

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
public class FuncaoTecnicaFuncionarioController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/funcoes-tecnicas-funcionario")
    public String listFuncoesTecnicasFuncionario(Model model) {
        // Retrieve the list of funcoes tecnicas funcionario from the database
        List<FuncaoTecnicaFuncionario> funcoesTecnicasFuncionario = jdbcTemplate.query(
                "SELECT * FROM funcao_tecnica_funcionario",
                new FuncaoTecnicaFuncionarioRowMapper());
        model.addAttribute("listaFuncoesTecnicasFuncionario", funcoesTecnicasFuncionario);
        return "funcoes-tecnicas-funcionario"; // Assuming you have a funcoes-tecnicas-funcionario.html template
    }

    @GetMapping("/funcao-tecnica-funcionario")
    public String formFuncaoTecnicaFuncionario(Model model) {
        model.addAttribute("funcaoTecnicaFuncionario", new FuncaoTecnicaFuncionario());
        return "funcao-tecnica-funcionario"; // Assuming you have a funcao-tecnica-funcionario.html template
    }

    @GetMapping("/funcao-tecnica-funcionario/{id}")
    public String editFuncaoTecnicaFuncionario(Model model, @PathVariable Integer id) {
        FuncaoTecnicaFuncionario funcaoTecnicaFuncionario = jdbcTemplate.queryForObject(
                "SELECT * FROM funcao_tecnica_funcionario WHERE id = ?",
                new FuncaoTecnicaFuncionarioRowMapper(), id);
        model.addAttribute("funcaoTecnicaFuncionario", funcaoTecnicaFuncionario);
        return "funcao-tecnica-funcionario"; // Assuming you have a funcao-tecnica-funcionario.html template
    }

    @PostMapping("/funcao-tecnica-funcionario")
    public String submitFuncaoTecnicaFuncionario(@ModelAttribute FuncaoTecnicaFuncionario funcaoTecnicaFuncionario, Model model) {
        if (funcaoTecnicaFuncionario.getId() != null && funcaoTecnicaFuncionario.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE funcao_tecnica_funcionario SET func_tec = ?, id_func = ? WHERE id = ?",
                    funcaoTecnicaFuncionario.getFuncTec(),
                    funcaoTecnicaFuncionario.getIdFuncionario(),
                    funcaoTecnicaFuncionario.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO funcao_tecnica_funcionario (func_tec, id_func) VALUES (?, ?)",
                    funcaoTecnicaFuncionario.getFuncTec(),
                    funcaoTecnicaFuncionario.getIdFuncionario());
        }
        return "redirect:/funcoes-tecnicas-funcionario"; // Redirect to the funcoes-tecnicas-funcionario endpoint
    }

    @DeleteMapping("/funcao-tecnica-funcionario/{id}")
    public String deleteFuncaoTecnicaFuncionario(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM funcao_tecnica_funcionario WHERE id = ?",
                id);
        return "redirect:/funcoes-tecnicas-funcionario"; // Redirect to the funcoes-tecnicas-funcionario endpoint
    }
}
