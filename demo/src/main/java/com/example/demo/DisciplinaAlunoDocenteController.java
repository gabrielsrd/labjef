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
public class DisciplinaAlunoDocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/disciplinas-alunos-docentes")
    public String listDisciplinasAlunoDocente(Model model) {
        // Retrieve the list of disciplinas aluno docente from the database
        List<DisciplinaAlunoDocente> disciplinasAlunoDocente = jdbcTemplate.query(
                "SELECT * FROM disciplina_aluno_docente",
                new DisciplinaAlunoDocenteRowMapper());
        model.addAttribute("listaDisciplinasAlunosDocentes", disciplinasAlunoDocente);
        return "disciplinas-alunos-docentes"; // Assuming you have a disciplinas-aluno-docente.html template
    }

    @GetMapping("/disciplina-aluno-docente")
    public String formDisciplinaAlunoDocente(Model model) {
        model.addAttribute("disciplinaAlunoDocente", new DisciplinaAlunoDocente());
        return "disciplina-aluno-docente"; // Assuming you have a disciplina-aluno-docente.html template
    }

    @GetMapping("/disciplina-aluno-docente/{id}")
    public String editDisciplinaAlunoDocente(Model model, @PathVariable Integer id) {
        DisciplinaAlunoDocente disciplinaAlunoDocente = jdbcTemplate.queryForObject(
                "SELECT * FROM disciplina_aluno_docente WHERE id = ?",
                new DisciplinaAlunoDocenteRowMapper(), id);
        model.addAttribute("disciplinaAlunoDocente", disciplinaAlunoDocente);
        return "disciplina-aluno-docente"; // Assuming you have a disciplina-aluno-docente.html template
    }

    @PostMapping("/disciplina-aluno-docente")
    public String submitDisciplinaAlunoDocente(@ModelAttribute DisciplinaAlunoDocente disciplinaAlunoDocente, Model model) {
        if (disciplinaAlunoDocente.getId() != null && disciplinaAlunoDocente.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE disciplina_aluno_docente SET id_aluno = ?, id_docente = ?, id_disc = ?, data_inic = ?, data_fim = ?, nota = ? WHERE id = ?",
                    disciplinaAlunoDocente.getIdAluno(),
                    disciplinaAlunoDocente.getIdDocente(),
                    disciplinaAlunoDocente.getIdDisciplina(),
                    disciplinaAlunoDocente.getDataInicio(),
                    disciplinaAlunoDocente.getDataFim(),
                    disciplinaAlunoDocente.getNota(),
                    disciplinaAlunoDocente.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO disciplina_aluno_docente (id_aluno, id_docente, id_disc, data_inic, data_fim, nota) VALUES (?, ?, ?, ?, ?, ?)",
                    disciplinaAlunoDocente.getIdAluno(),
                    disciplinaAlunoDocente.getIdDocente(),
                    disciplinaAlunoDocente.getIdDisciplina(),
                    disciplinaAlunoDocente.getDataInicio(),
                    disciplinaAlunoDocente.getDataFim(),
                    disciplinaAlunoDocente.getNota());
        }
        return "redirect:/disciplinas-alunos-docentes"; // Redirect to the disciplinas-aluno-docente endpoint
    }

    @DeleteMapping("/disciplina-aluno-docente/{id}")
    public String deleteDisciplinaAlunoDocente(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM disciplina_aluno_docente WHERE id = ?",
                id);
        return "redirect:/disciplinas-alunos-docentes"; // Redirect to the disciplinas-aluno-docente endpoint
    }
}
