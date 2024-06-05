package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.service.ManagerService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stores")
@AllArgsConstructor
public class StoreController {
    private StoreService storeService;
//    private ManagerService managerService;

    @GetMapping("/list")
    public String storeList(Model model){
        List<Store> storeList = storeService.findAll();
        model.addAttribute("storeList", storeList);
        return "stores/store_list";
    }

    @GetMapping("/new")
    public String addStoreForm(Model model){
        model.addAttribute("store", new Store());
        //暂时不添加"选择 store manager" 功能
//        List<Manager> managerList = managerService.findAll();
//        model.addAttribute("managerList", managerList);
        return "stores/store_form";
    }

    @GetMapping("/update/{id}")
    public String updateStore(@PathVariable("id") int id, Model model){
        Store store = storeService.findById(id);
        model.addAttribute("store", store);
        return "stores/store_form";
    }

    @PostMapping("/save")
    public String saveStore(Store store){
        storeService.save(store);
        return "redirect:/stores/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable("id") int id){
        storeService.deleteById(id);
        return "redirect:/stores/list";
    }
}
