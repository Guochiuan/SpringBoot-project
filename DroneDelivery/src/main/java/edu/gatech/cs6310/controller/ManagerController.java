package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Manager;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.service.ManagerService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/managers")
@AllArgsConstructor
public class ManagerController {
    private ManagerService managerService;

    @GetMapping("/list")
    public String managerList(Model model){
        List<Manager> managerList = managerService.findAll();
        model.addAttribute("managerList", managerList);
        return "managers/manager_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteManager(@PathVariable("id") int id){
        managerService.deleteById(id);
        return "redirect:/managers/list";
    }
}