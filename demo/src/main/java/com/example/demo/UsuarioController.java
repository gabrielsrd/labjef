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
public class UsuarioController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/usuarios")
    public String listaUsuarios(Model model) {
        List<Usuario> usuarios = jdbcTemplate.query(
                "SELECT * FROM usuario ORDER BY id DESC",
                new UsuarioRowMapper());
        model.addAttribute("listaUsuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/usuario")
    public String formUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario";
    }

    @GetMapping("/usuario/{id}")
    public String editUsuario(Model model, @PathVariable Integer id) {
        Usuario usuario = jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id = ?",
                new UsuarioRowMapper(), id);
        model.addAttribute("usuario", usuario);
        return "usuario";
    }

    @PostMapping("/usuario")
    public String submitUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuario.getId() != null && usuario.getId() > 0) {
            jdbcTemplate.update(
                    "UPDATE usuario SET cpf = ?, nome = ?, login = ?, senha = ?, data_nasc = ?, instituicao = ?, endereco = ? WHERE id = ?",
                    usuario.getCpf(),
                    usuario.getNome(),
                    usuario.getLogin(),
                    usuario.getSenha(),
                    usuario.getDataNasc(),
                    usuario.getInstituicao(),
                    usuario.getEndereco(),
                    usuario.getId());
        } else {
            String sql = "INSERT INTO usuario (cpf, nome, login, senha, data_nasc, instituicao, endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";
            Object[] values = {
                    usuario.getCpf(),
                    usuario.getNome(),
                    usuario.getLogin(),
                    usuario.getSenha(),
                    usuario.getDataNasc(),
                    usuario.getInstituicao(),
                    usuario.getEndereco()
            };

            try {
                jdbcTemplate.update(sql, values);
            } catch (Exception e) {
                System.out.println("Error executing SQL statement: " + sql);
                e.printStackTrace();
                throw e;
            }
        }
        return "redirect:/usuarios";
    }

    @DeleteMapping("/usuario/{id}")
    public String deleteUsuario(@PathVariable Integer id) {
        jdbcTemplate.update(
                "DELETE FROM usuario WHERE id = ?",
                id);
        return "redirect:/usuarios";
    }
}
