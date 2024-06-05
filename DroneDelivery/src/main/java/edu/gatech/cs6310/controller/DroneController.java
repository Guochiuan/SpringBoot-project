package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Manager;
import edu.gatech.cs6310.entity.Orders;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.service.DroneService;
import edu.gatech.cs6310.service.ManagerService;
import edu.gatech.cs6310.service.OrdersService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/drones")
public class DroneController {
    DroneService droneService;
    OrdersService ordersService;
    StoreService storeService;
    ManagerService managerService;

    @GetMapping("/list")
    public String droneList(Authentication auth, Model model){
        List<Drone> droneList;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"))) {
            var manager = managerService.findByAccount_name(auth.getName());
            droneList = droneService.findAllByStore(manager.getStore());
        } else {
            droneList = droneService.findAll();
        }

        model.addAttribute("droneList", droneList);
        return "drones/drone_list";
    }

    @GetMapping("/new")
    public String addDroneForm(Model model){
        model.addAttribute("drone", new Drone());
        return "drones/drone_form";
    }

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable("id") int id, Model model){
        Drone drone = droneService.findDroneById(id);
        model.addAttribute("drone", drone);
        return "drones/drone_form";
    }

    @PostMapping("/save")
    public String saveDrone(Drone drone){
        droneService.save(drone);
        return "redirect:/drones/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDrone(@PathVariable("id") int id){
        droneService.deleteById(id);
        return "redirect:/drones/list";
    }
}
