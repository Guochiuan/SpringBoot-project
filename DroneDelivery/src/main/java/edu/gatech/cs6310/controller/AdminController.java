package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import java.sql.ResultSet;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/admin/logs")
    public String viewLogs(Model model){
        String sql = "SELECT * FROM logs LIMIT 0, 1000;";

        List<String> res = jdbcTemplate.queryForList(sql, String.class);

        model.addAttribute("logs", res);

        return "admin/logs";
    }

    @GetMapping("/admin")
    public String adminHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("role", authentication.getName());
        return "admin/admin_home";
    }
}
