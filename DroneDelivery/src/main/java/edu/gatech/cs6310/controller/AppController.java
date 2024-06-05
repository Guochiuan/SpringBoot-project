package edu.gatech.cs6310.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/homepage")
    public String viewHomepage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String sql = String.format("SELECT authority FROM authorities where username = '%s';", username);
        List<String> authority_res = jdbcTemplate.queryForList(sql, String.class);

        // get manager name

        //get customer name

        // get pilot name


        if( authority_res.isEmpty() )
            return "redirect:/showMyLoginPage";
        String authority = authority_res.get(0);

        if(authority.equals("ROLE_CUSTOMER"))
            return "redirect:/customers";
        else if(authority.equals("ROLE_PILOT")) {
            return "redirect:/pilot_user";
        }
        else if(authority.equals("ROLE_MANAGER"))
            return "redirect:/manager_user";
        else if(authority.equals("ROLE_ADMIN"))
            return "redirect:/admin";
        else
            return "redirect:/showMyLoginPage";
    }
}
