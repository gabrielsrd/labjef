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
public class ServicoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/servicos")
    public String listServicos(Model model) {
        // Retrieve the list of services from the database
        List<Servico> servicos = jdbcTemplate.query(
                "SELECT * FROM servico",
                new ServicoRowMapper());
        model.addAttribute("listaServicos", servicos);
        return "servicos"; // Assuming you have a servicos.html template
    }

    @GetMapping("/servico")
    public String formServico(Model model) {
        model.addAttribute("servico", new Servico());
        return "servico"; // Assuming you have a servico.html template
    }

    @GetMapping("/servico/{id}")
    public String editServico(Model model, @PathVariable Integer id) {
        Servico servico = jdbcTemplate.queryForObject("SELECT * FROM servico WHERE id_serv = ?",
                new ServicoRowMapper(), id);
        model.addAttribute("servico", servico);
        return "servico"; // Assuming you have a servico.html template
    }

    @PostMapping("/servico")
    public String submitServico(@ModelAttribute Servico servico, Model model) {
        if (servico.getIdServ() != null && servico.getIdServ() > 0) {
            jdbcTemplate.update(
                    "UPDATE servico SET descricao = ?, tipo_serv = ?, cod_serv = ?, id_perfil = ? WHERE id_serv = ?",
                    servico.getDescricao(),
                    servico.getTipoServ(),
                    servico.getCodServ(),
                    servico.getIdPerfil(),
                    servico.getIdServ());
        } else {
            jdbcTemplate.update(
                    "INSERT INTO servico (descricao, tipo_serv, cod_serv, id_perfil) VALUES (?, ?, ?, ?)",
                    servico.getDescricao(),
                    servico.getTipoServ(),
                    servico.getCodServ(),
                    servico.getIdPerfil());
        }
        return "redirect:/servicos"; // Redirect to the servicos endpoint
    }

    @DeleteMapping("/servico/{id}")
    public String deleteServico(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM servico WHERE id_serv = ?",
                id);
        return "redirect:/servicos"; // Redirect to the servicos endpoint
    }
}