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
import java.util.List;

@Controller
public class FuncaoTecnicaDocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/funcoes-tecnicas-docente")
    public String listFuncoesTecnicasDocente(Model model) {
        // Retrieve the list of funcoes tecnicas docente from the database
        List<FuncaoTecnicaDocente> funcoesTecnicasDocente = jdbcTemplate.query(
                "SELECT * FROM funcao_tecnica_docente",
                new FuncaoTecnicaDocenteRowMapper());
        model.addAttribute("listaFuncoesTecnicasDocente", funcoesTecnicasDocente);
        return "funcoes-tecnicas-docente"; // Assuming you have a funcoes-tecnicas-docente.html template
    }

    @GetMapping("/funcao-tecnica-docente")
    public String formFuncaoTecnicaDocente(Model model) {
        model.addAttribute("funcaoTecnicaDocente", new FuncaoTecnicaDocente());
        return "funcao-tecnica-docente"; // Assuming you have a funcao-tecnica-docente.html template
    }

    @GetMapping("/funcao-tecnica-docente/{id}")
    public String editFuncaoTecnicaDocente(Model model, @PathVariable Integer id) {
        FuncaoTecnicaDocente funcaoTecnicaDocente = jdbcTemplate.queryForObject(
                "SELECT * FROM funcao_tecnica_docente WHERE id = ?",
                new FuncaoTecnicaDocenteRowMapper(), id);
        model.addAttribute("funcaoTecnicaDocente", funcaoTecnicaDocente);
        return "funcao-tecnica-docente"; // Assuming you have a funcao-tecnica-docente.html template
    }

    @PostMapping("/funcao-tecnica-docente")
    public String submitFuncaoTecnicaDocente(@ModelAttribute FuncaoTecnicaDocente funcaoTecnicaDocente, Model model) {
        if (funcaoTecnicaDocente.getId() != null && funcaoTecnicaDocente.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE funcao_tecnica_docente SET func_tec = ?, id_docente = ? WHERE id = ?",
                    funcaoTecnicaDocente.getFuncTec(),
                    funcaoTecnicaDocente.getIdDocente(),
                    funcaoTecnicaDocente.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO funcao_tecnica_docente (func_tec, id_docente) VALUES (?, ?)",
                    funcaoTecnicaDocente.getFuncTec(),
                    funcaoTecnicaDocente.getIdDocente());
        }
        return "redirect:/funcoes-tecnicas-docente"; // Redirect to the funcoes-tecnicas-docente endpoint
    }

    @DeleteMapping("/funcao-tecnica-docente/{id}")
    public String deleteFuncaoTecnicaDocente(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM funcao_tecnica_docente WHERE id = ?",
                id);
        return "redirect:/funcoes-tecnicas-docente"; // Redirect to the funcoes-tecnicas-doc
    }
}
