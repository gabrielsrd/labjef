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

import java.sql.Date;
import java.util.List;

@Controller
public class RelDisciplinaAlunoDocenteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/rel-disciplinas-alunos-docentes")
    public String listDisciplinasAlunoDocente(Model model) {
        List<RelDisciplinaAlunoDocente> relDisciplinasAlunosDocentes = jdbcTemplate.query(
                "SELECT * FROM rel_disciplina_aluno_docente",
                new RelDisciplinaAlunoDocenteRowMapper());
        model.addAttribute("listaRelDisciplinasAlunosDocentes", relDisciplinasAlunosDocentes);
        return "rel-disciplinas-alunos-docentes";
    }

    @GetMapping("/rel-disciplina-aluno-docente")
    public String formDisciplinaAlunoDocente(Model model) {
        model.addAttribute("relDisciplinaAlunoDocente", new RelDisciplinaAlunoDocente());
        return "rel-disciplina-aluno-docente";
    }

    @GetMapping("/rel-disciplina-aluno-docente/{id}")
    public String editDisciplinaAlunoDocente(Model model, @PathVariable Integer id) {
        RelDisciplinaAlunoDocente relDisciplinaAlunoDocente = jdbcTemplate.queryForObject(
                "SELECT * FROM rel_disciplina_aluno_docente WHERE id = ?",
                new RelDisciplinaAlunoDocenteRowMapper(), id);
        model.addAttribute("relDisciplinaAlunoDocente", relDisciplinaAlunoDocente);
        return "rel-disciplina-aluno-docente";
    }

    @PostMapping("/rel-disciplina-aluno-docente")
    public String submitDisciplinaAlunoDocente(@ModelAttribute RelDisciplinaAlunoDocente relDisciplinaAlunoDocente, Model model) {
        Date dataFim = null;
        if (relDisciplinaAlunoDocente.getDataFim() != "") {// string to date
            dataFim = Date.valueOf(relDisciplinaAlunoDocente.getDataFim());
        }
        Float nota = null;
        if (relDisciplinaAlunoDocente.getNota() != "") {// string to float
            nota = Float.valueOf(relDisciplinaAlunoDocente.getNota());
        }

        if (relDisciplinaAlunoDocente.getId() != null && relDisciplinaAlunoDocente.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE rel_disciplina_aluno_docente SET id_aluno = ?, id_docente = ?, id_disc = ?, data_inic = ?, data_fim = ?, nota = ? WHERE id = ?",
                    relDisciplinaAlunoDocente.getIdAluno(),
                    relDisciplinaAlunoDocente.getIdDocente(),
                    relDisciplinaAlunoDocente.getIdDisciplina(),
                    relDisciplinaAlunoDocente.getDataInicio(),
                    dataFim,
                    nota,
                    relDisciplinaAlunoDocente.getId());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO rel_disciplina_aluno_docente (id_aluno, id_docente, id_disc, data_inic, data_fim, nota) VALUES (?, ?, ?, ?, ?, ?)",
                    relDisciplinaAlunoDocente.getIdAluno(),
                    relDisciplinaAlunoDocente.getIdDocente(),
                    relDisciplinaAlunoDocente.getIdDisciplina(),
                    relDisciplinaAlunoDocente.getDataInicio(),
                    dataFim,
                    nota);
        }
        return "redirect:/rel-disciplinas-alunos-docentes";
    }

    @DeleteMapping("/rel-disciplina-aluno-docente/{id}")
    public String deleteDisciplinaAlunoDocente(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM rel_disciplina_aluno_docente WHERE id = ?",
                id);
        return "redirect:/rel-disciplinas-alunos-docentes";
    }
}
