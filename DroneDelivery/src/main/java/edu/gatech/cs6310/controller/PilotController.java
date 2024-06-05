package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Orders;
import edu.gatech.cs6310.entity.Pilot;
import edu.gatech.cs6310.service.DroneService;
import edu.gatech.cs6310.service.OrdersService;
import edu.gatech.cs6310.service.PilotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pilots")
@AllArgsConstructor
@Slf4j
public class PilotController {
    private PilotService pilotService;
    private DroneService droneService;
    private OrdersService ordersService;

    @GetMapping("/list")
    public String pilotList(Model model){
        List<Pilot> pilotList = pilotService.findAll();
        model.addAttribute("pilotList", pilotList);
        return "pilots/pilot_list";
    }

    @GetMapping("/delete/{id}")
    public String deletePilot(@PathVariable("id") int id) {
        pilotService.deleteById(id);
        return "redirect:/pilots/list";
    }

    @GetMapping("/pair")
    public String pairWithADrone(Principal principal, Model model) {
        var pilot = pilotService.findByAccountName(principal.getName()).get();
        if (pilot.getDrone() != null) {
            String output = String.format("Rejected: Pilot %s already paired with drone %s", pilot.getId(), pilot.getDrone().getId());
            log.info(output);
            model.addAttribute("message", output);
            return "pilots/pilot_done";
        }

        var findDrone = droneService.findUnpairedAndLoadedDrone();
        if (!findDrone.isPresent()) {
            String output = "Failed: no drone found that is both unpaired and loaded with orders";
            log.info(output);
            model.addAttribute("message", output);
            return "pilots/pilot_done";
        }
        var drone = findDrone.get();

        pilot.setDrone(drone);
        pilotService.saveAndFlush(pilot);

        log.info(String.format("Pilot %s paired with drone %s", pilot.getId(), drone.getId()));
        model.addAttribute("message",
                String.format("Pilot %s paired with drone %s", pilot.getId(), drone.getId()));
        return "pilots/pilot_done";
    }

    @GetMapping("/fly")
    public String flyDrone(Principal principal, Model model) {
        var pilot = pilotService.findByAccountName(principal.getName()).get();
        if (pilot.getDrone() == null) {
            String output = String.format("Rejected: Pilot %s is not paired with any drone", pilot.getId());
            log.info(output);
            model.addAttribute("message", output);
            return "pilots/pilot_done";
        }
        // update pilot info
        // save drone id for later use
        var droneId = pilot.getDrone().getId();
        pilot.setDrone(null);
        pilot.setExpLevel(pilot.getExpLevel() + 1);
        pilotService.saveAndFlush(pilot);
        log.info(String.format("updated pilot %s", pilot.getId()));

        // update drone related info
        var drone = droneService.findById(droneId).get();
        drone.setNumOrders(0);
        int newNumberDeliveriesLeft = drone.getNumDeliveriesLeft() - 1;
        if (newNumberDeliveriesLeft >= 0) {
            drone.setNumDeliveriesLeft(newNumberDeliveriesLeft);
        }
        drone.setAvailableCap(drone.getLiftCap());
        droneService.saveAndFlush(drone);
        log.info(String.format("updated drone %s", drone.getId()));

        // update order info
        List<Orders> orders = ordersService.findByDrone(drone);
        for (Orders order : orders) {
            var st = order.getStore();
            st.setCompletedRevenue(st.getCompletedRevenue() + order.getTotalCost());
            st.setIncomingRevenue(st.getIncomingRevenue() - order.getTotalCost());
            ordersService.deleteById(order.getId());
            log.info(String.format("deleted order %s", order.getId()));
        }
        ordersService.flush();

        model.addAttribute("message",
                String.format("Finished all order deliveries on drone %s", drone.getId()));
        return "pilots/pilot_done";
    }
}
