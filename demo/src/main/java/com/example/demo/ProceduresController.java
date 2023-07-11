package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProceduresController {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public ProceduresController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @GetMapping("/servico-pode")
    public String getCanServices(Model model) {
        String sql = "SELECT * FROM get_usuario_servico()";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP1", rows);
        return "p1"; // Name of the HTML template file
    }

    @GetMapping("/servicos-usados")
    public String getUsedServices(Model model) {
        String sql = "SELECT * FROM get_total_servicos_por_perfil()";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP2", rows);
        return "p2"; // Name of the HTML template file
    }

    @GetMapping("/servicos-usados")
    public String getTopDisciplinas(Model model) {
        String sql = "SELECT * FROM get_total_servicos_por_perfil()";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP2", rows);
        return "p2"; // Name of the HTML template file
    }
}
