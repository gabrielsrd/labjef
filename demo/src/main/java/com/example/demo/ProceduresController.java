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

    @GetMapping("/top-disciplinas")
    public String getTopDisciplinas(Model model) {
        String sql = "SELECT * FROM listar_disciplinas_oferecidas()";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP3", rows);
        sql = "SELECT * FROM lista_5()";
        List<Map<String, Object>> rows1 = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP3top", rows1);
        return "p3"; // Name of the HTML template file
    }

    @GetMapping("/top-docentes")
    public String getTopDocentes(Model model) {
        String sql = "SELECT * FROM listar_docentes_mais_ministraram()";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("listaP4", rows);
        return "p4"; // Name of the HTML template file
    }
}
