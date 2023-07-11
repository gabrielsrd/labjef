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
public class EspecialidadeDocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/especialidades-docente")
    public String listEspecialidadesDocente(Model model) {
        // Retrieve the list of especialidades docente from the database
        List<EspecialidadeDocente> especialidadesDocente = jdbcTemplate.query(
                "SELECT * FROM especialidade_docente",
                new EspecialidadeDocenteRowMapper());
        model.addAttribute("listaEspecialidadesDocente", especialidadesDocente);
        return "especialidades-docente"; // Assuming you have an especialidades-docente.html template
    }

    @GetMapping("/especialidade-docente")
    public String formEspecialidadeDocente(Model model) {
        model.addAttribute("especialidadeDocente", new EspecialidadeDocente());
        return "especialidade-docente"; // Assuming you have an especialidade-docente.html template
    }

    @GetMapping("/especialidade-docente/{id}")
    public String editEspecialidadeDocente(Model model, @PathVariable Integer id) {
        EspecialidadeDocente especialidadeDocente = jdbcTemplate.queryForObject(
                "SELECT * FROM especialidade_docente WHERE id = ?",
                new EspecialidadeDocenteRowMapper(), id);
        model.addAttribute("especialidadeDocente", especialidadeDocente);
        return "especialidade-docente"; // Assuming you have an especialidade-docente.html template
    }

    @PostMapping("/especialidade-docente")
    public String submitEspecialidadeDocente(@ModelAttribute EspecialidadeDocente especialidadeDocente, Model model) {
        if (especialidadeDocente.getId() != null && especialidadeDocente.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE especialidade_docente SET especialidade = ?, id_docente = ? WHERE id = ?",
                    especialidadeDocente.getEspecialidade(),
                    especialidadeDocente.getIdDocente(),
                    especialidadeDocente.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO especialidade_docente (especialidade, id_docente) VALUES (?, ?)",
                    especialidadeDocente.getEspecialidade(),
                    especialidadeDocente.getIdDocente());
        }
        return "redirect:/especialidades-docente"; // Redirect to the especialidades-docente endpoint
    }

    @DeleteMapping("/especialidade-docente/{id}")
    public String deleteEspecialidadeDocente(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM especialidade_docente WHERE id = ?",
                id);
        return "redirect:/especialidades-docente"; // Redirect to the especialidades-docente endpoint
    }
}
