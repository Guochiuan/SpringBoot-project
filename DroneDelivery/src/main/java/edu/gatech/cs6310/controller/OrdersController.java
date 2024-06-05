package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.dto.LineDto;
import edu.gatech.cs6310.dto.OrderDto;
import edu.gatech.cs6310.entity.*;
import edu.gatech.cs6310.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {
    OrdersService ordersService;
    StoreService storeService;
    StoreItemService storeItemService;
    ItemService itemService;
    CustomerService customerService;
    DroneService droneService;

    @GetMapping("/list")
    public String orderList(Authentication auth, Model model){
        List<Orders> orderList;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            var customer = customerService.findCustomerByAccountName(auth.getName()).get();
            orderList = ordersService.findAllByCustomer(customer);
        } else {
            orderList = ordersService.findAll();
        }
        model.addAttribute("orderList", orderList);
        return "orders/order_list";
    }

    @GetMapping("/new")
    public String addOrderForm(Model model){
        var orderDto = new OrderDto();
        orderDto.setQuantity(1);
        model.addAttribute("orderDto", orderDto);
        addStoresAndItemModel(model);
        return "orders/order_form";
    }

    @PostMapping(value = "/new", params = {"addItem"})
    public String addItem(@ModelAttribute OrderDto orderDto, Model model){
        var storeItem = storeItemService.findById(orderDto.getStoreItemId()).get();

        LineDto lineDto = new LineDto();
        lineDto.setItemId(storeItem.getItem().getId());
        lineDto.setCost(storeItem.getPrice());
        lineDto.setQuantity(orderDto.getQuantity());
        lineDto.setName(storeItem.getItem().getName());
        lineDto.setWeight(storeItem.getItem().getWeight());

        orderDto.getLines().add(lineDto);
        addStoresAndItemModel(model);
        return "orders/order_form";
    }

    @PostMapping(value = "/new", params = {"removeItem"})
    public String removeItem(
            @RequestParam("removeItem") int index,
            @ModelAttribute OrderDto orderDto,
            Model model) {
        orderDto.getLines().remove(index);
        addStoresAndItemModel(model);
        return "orders/order_form";
    }

    @PostMapping(value = "/new", params = {"selectStore"})
    public String removeItem(@ModelAttribute OrderDto orderDto, Model model) {
        addStoresAndItemModel(model);
        return "orders/order_form";
    }

    @PostMapping("/new")
    public String saveOrder(@ModelAttribute OrderDto orderDto, Principal principal, Model model){
        // calculate total price and weight
        int totalCost = 0;
        int totalWeight = 0;
        for (var line: orderDto.getLines()) {
            totalCost += line.getCost() * line.getQuantity();
            totalWeight += line.getWeight() * line.getQuantity();
        }

        // check customer's money
        var customer = customerService.findCustomerByAccountName(principal.getName()).get();
        var credits = customer.getAvailableCredit();
        if (credits < totalCost) {
            model.addAttribute("errorMsg", "failed: inefficient credit");
            addStoresAndItemModel(model);
            return "orders/order_form";
        }

        // calculate total weight
        var drones = droneService.findDronesForOrder(orderDto.getStoreId(), totalWeight);
        if (drones == null || drones.size() == 0) {
            model.addAttribute("errorMsg", "failed: no drone available");
            addStoresAndItemModel(model);
            return "orders/order_form";
        }

        // update customer credit
        customer.setAvailableCredit(credits - totalCost);

        // assign order to a drone
        var drone = drones.get(0);
        var store = storeService.findById(orderDto.getStoreId());
        var order = new Orders(
                orderDto.getOrderNumber(),
                drone,
                customer,
                store,
                null,
                totalCost,
                totalWeight);
        List<Line> lines = new ArrayList<>();
        for (var lineDto : orderDto.getLines()) {
            var item = itemService.findById(lineDto.getItemId());
            var line = new Line(item,
                    order,
                    lineDto.getQuantity(),
                    lineDto.getCost(),
                    lineDto.getWeight());
//            item.setLine(line);
            lines.add(line);
        }
        order.setOrderLines(lines);

        // update drone
        drone.addOrder(order);

        // update store
        store.setIncomingRevenue(store.getIncomingRevenue() + order.getTotalCost());

        storeService.save(store);
        ordersService.save(order);
        customerService.save(customer);
        droneService.saveAndFlush(drone);

        return "redirect:/orders/list";
    }

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable("id") int id, Model model){
        Orders order = ordersService.findById(id);
        model.addAttribute("order", order);

        List<Store> storeList = storeService.findAll();
        model.addAttribute("storeList", storeList);
        return "orders/order_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        ordersService.deleteById(id);
        return "redirect:/orders/list";
    }

    @GetMapping("/itemDropdown")
    public String getItemDropdown(@RequestParam int storeId, Model model) {
        var store = storeService.findById(storeId);
        model.addAttribute("storeItems", store.getStoreItems());
        return "/fragments/orders_fragments :: dropdown-items";
    }

    private void addStoresAndItemModel(Model model) {
        var orderDto = (OrderDto) model.getAttribute("orderDto");
        List<Store> storeList = storeService.findAll();
        List<StoreItem> storeItems;
        if (orderDto.getStoreId() > 0) {
            storeItems = storeService.findById(orderDto.getStoreId()).getStoreItems();
        } else {
            storeItems = storeList.get(0).getStoreItems();
        }

        model.addAttribute("storeList", storeList);
        model.addAttribute("storeItems", storeItems);
    }
}
