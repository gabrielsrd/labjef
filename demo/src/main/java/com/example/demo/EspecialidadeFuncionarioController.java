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
public class EspecialidadeFuncionarioController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/especialidades-funcionario")
    public String listEspecialidadesFuncionario(Model model) {
        // Retrieve the list of especialidades funcionario from the database
        List<EspecialidadeFuncionario> especialidadesFuncionario = jdbcTemplate.query(
                "SELECT * FROM especialidade_funcionario",
                new EspecialidadeFuncionarioRowMapper());
        model.addAttribute("listaEspecialidadesFuncionario", especialidadesFuncionario);
        return "especialidades-funcionario"; // Assuming you have an especialidades-funcionario.html template
    }

    @GetMapping("/especialidade-funcionario")
    public String formEspecialidadeFuncionario(Model model) {
        model.addAttribute("especialidadeFuncionario", new EspecialidadeFuncionario());
        return "especialidade-funcionario"; // Assuming you have an especialidade-funcionario.html template
    }

    @GetMapping("/especialidade-funcionario/{id}")
    public String editEspecialidadeFuncionario(Model model, @PathVariable Integer id) {
        EspecialidadeFuncionario especialidadeFuncionario = jdbcTemplate.queryForObject(
                "SELECT * FROM especialidade_funcionario WHERE id = ?",
                new EspecialidadeFuncionarioRowMapper(), id);
        model.addAttribute("especialidadeFuncionario", especialidadeFuncionario);
        return "especialidade-funcionario"; // Assuming you have an especialidade-funcionario.html template
    }

    @PostMapping("/especialidade-funcionario")
    public String submitEspecialidadeFuncionario(@ModelAttribute EspecialidadeFuncionario especialidadeFuncionario, Model model) {
        if (especialidadeFuncionario.getId() != null && especialidadeFuncionario.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE especialidade_funcionario SET especialidade = ?, id_func = ? WHERE id = ?",
                    especialidadeFuncionario.getEspecialidade(),
                    especialidadeFuncionario.getIdFuncionario(),
                    especialidadeFuncionario.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO especialidade_funcionario (especialidade, id_func) VALUES (?, ?)",
                    especialidadeFuncionario.getEspecialidade(),
                    especialidadeFuncionario.getIdFuncionario());
        }
        return "redirect:/especialidades-funcionario"; // Redirect to the especialidades-funcionario endpoint
    }

    @DeleteMapping("/especialidade-funcionario/{id}")
    public String deleteEspecialidadeFuncionario(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM especialidade_funcionario WHERE id = ?",
                id);
        return "redirect:/especialidades-funcionario"; // Redirect to the especialidades-funcionario endpoint
    }
}
