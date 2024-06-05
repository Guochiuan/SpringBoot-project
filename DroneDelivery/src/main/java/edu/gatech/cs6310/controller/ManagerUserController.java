package edu.gatech.cs6310.controller;

//TODO: Store Owners are authorized to conduct operations such as sell_item, display_items,
// Jack 或者 Minjia & Anqi 负责？

import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.service.ManagerService;
import edu.gatech.cs6310.service.PilotService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/manager_user")
@AllArgsConstructor
public class ManagerUserController {
    private ManagerService managerService;
    private StoreService storeService;

    @GetMapping("")
    public String managerHome(Principal principal, Model model){
        var manager = managerService.findByAccount_name(principal.getName());
        int targetStoreId = manager.getId();
        var store = storeService.findByManagerId(targetStoreId);

        model.addAttribute("storeId", targetStoreId);
        model.addAttribute("manager", manager);
        model.addAttribute("store",store);

        return "managers/manager_home";
    }
}
