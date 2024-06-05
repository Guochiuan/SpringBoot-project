package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Orders;
import edu.gatech.cs6310.entity.Pilot;
import edu.gatech.cs6310.service.DroneService;
import edu.gatech.cs6310.service.OrdersService;
import edu.gatech.cs6310.service.PilotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/pilot_user")
@Slf4j
public class PilotUserController {
    private DroneService droneService;
    private PilotService pilotService;
    private OrdersService ordersService;

    @GetMapping()
    public String pilotHomePage(Principal principal, Model model) {
        var pilot = pilotService.findByAccountName(principal.getName());
        model.addAttribute("pilot", pilot.get());
        return "pilots/pilot_home";
    }

    @GetMapping("/pair")
    @ResponseBody
    public String pairWithADrone(Principal principal) {
        var pilot = pilotService.findByAccountName(principal.getName()).get();
        if (pilot.getDrone() != null) {
            String output = String.format("Rejected: Pilot %s already paired with drone %s", pilot.getId(), pilot.getDrone().getId());
            log.info(output);
            return output;
        }

        var findDrone = droneService.findUnpairedAndLoadedDrone();
        if (!findDrone.isPresent()) {
            String output = "Failed: no drone found that is both unpaired and loaded with orders";
            log.info(output);
            return output;
        }
        var drone = findDrone.get();

        pilot.setDrone(drone);
        pilotService.saveAndFlush(pilot);

        log.info(String.format("Pilot %s paired with drone %s", pilot.getId(), drone.getId()));
        return String.format("Pilot %s paired with drone %s", pilot.getId(), drone.getId());
    }

    @GetMapping("/fly")
    @ResponseBody
    public String flyDrone(Principal principal) {
        var pilot = pilotService.findByAccountName(principal.getName()).get();
        if (pilot.getDrone() == null) {
            String output = String.format("Rejected: Pilot %s is not paired with any drone", pilot.getId());
            log.info(output);
            return output;
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
        drone.setRemainingCap(drone.getAvailableCap());
        drone.setLiftCap(0);
        droneService.saveAndFlush(drone);
        log.info(String.format("updated drone %s", drone.getId()));

        // update order info
        List<Orders> orders = ordersService.findByDrone(drone);
        for (Orders order : orders) {
            ordersService.deleteById(order.getId());
            log.info(String.format("deleted order %s", order.getId()));
        }
        ordersService.flush();

        return String.format("Finished all order deliveries on drone %s", drone.getId());
    }
}
