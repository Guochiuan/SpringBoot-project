package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.dto.ItemDto;
import edu.gatech.cs6310.entity.Item;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.entity.StoreItem;
import edu.gatech.cs6310.service.ItemService;
import edu.gatech.cs6310.service.StoreItemService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor

@RequestMapping("/items")
public class ItemController {

    ItemService itemService;
    StoreService storeService;
    StoreItemService storeItemService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        String sql = String.format("SELECT authority FROM authorities where username = '%s';", username);
//        List<String> authority_res = jdbcTemplate.queryForList(sql, String.class);
    // get manager name





    @GetMapping("/list/{id}")//this "id" is store id
    public String itemList(@PathVariable("id") int id, Model model){
        Store store = storeService.findById(id);
        String storeName = store.getName();
        List<StoreItem> itemList = storeItemService.findByStore(store);
        model.addAttribute("itemList", itemList);
        return "items/item_list";
    }

    @GetMapping("/new/{id}")//this "id" is store id
    public String addItemForm(@PathVariable("id") int id, Model model){
        model.addAttribute("item", new Item());

        Store store = storeService.findById(id);
        List<Store> storeList = new ArrayList<>();
        storeList.add(store);
        model.addAttribute("storeList", storeList);
        model.addAttribute("itemDto", new ItemDto());
        return "items/item_form";
    }


    @GetMapping("/update/{name}")//this "id" is item id
    public String updateItem(@PathVariable("name") String name, Model model){
        Item item = itemService.findByName(name);
        var itemDto = new ItemDto(
                item.getName(),
                item.getWeight(),
                item.getPrice(),
                item.getStore().getId(),
                item.getId());

        if(item == null){
            System.out.println("item is null");
        }
        model.addAttribute("item", item);

        Store store = storeService.findById(item.getStore().getId());
        List<Store> storeList = new ArrayList<>();
        storeList.add(store);
        model.addAttribute("storeList", storeList);
        model.addAttribute("itemDto", itemDto);
        return "items/item_form";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute ItemDto itemDto){
        var store = storeService.findById(itemDto.getStoreId());
        boolean itemExisted = false;
        Item item;
        if (itemDto.getItemId() != 0) {
            // item already existed
            item = itemService.findById(itemDto.getItemId());
            item.setWeight(itemDto.getWeight());
            item.setName(itemDto.getName());
            itemExisted = true;
        } else {
            item = new Item(itemDto.getName(), itemDto.getWeight(), itemDto.getPrice(), store);
        }

        var curItem = itemService.save(item);

        StoreItem storeItem;
        if (itemExisted) {
            var findStoreItem = storeItemService.findByItem(item);
            if (findStoreItem.isPresent()) {
                storeItem = findStoreItem.get();
            } else {
                storeItem = new StoreItem();
            }
        } else {
            storeItem = new StoreItem();
        }
        storeItem.setItem(curItem);
        storeItem.setPrice(itemDto.getPrice());
        storeItem.setStore(store);
        storeItemService.save(storeItem);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String sql = String.format("SELECT authority FROM authorities where username = '%s';", username);
        List<String> authority_res = jdbcTemplate.queryForList(sql, String.class);
        String authority = authority_res.get(0);
        if(authority.equals("ROLE_MANAGER"))
            return "redirect:/manager_user";
        else
            return "redirect:/stores/list";
    }

//    @PostMapping("/save")
//    public String saveItem(Item item){
//        itemService.save(item);
//
//        StoreItem storeItem = new StoreItem();
//        Item curItem = itemService.findById(item.getId());
//        storeItem.setItem(curItem);
//        storeItem.setPrice(curItem.getPrice());
//        storeItem.setStore(item.getStore());
//        storeItemService.save(storeItem);
//
//        return "redirect:/stores/list";
//    }
//




    @GetMapping("/delete/{id}")//this "id" is item id
    public String deleteItem(@PathVariable("id") int id){
        int itemId = storeItemService.findById(id).get().getItem().getId();
        storeItemService.deleteById(id);
        itemService.deleteById(itemId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String sql = String.format("SELECT authority FROM authorities where username = '%s';", username);
        List<String> authority_res = jdbcTemplate.queryForList(sql, String.class);
        String authority = authority_res.get(0);
        if(authority.equals("ROLE_MANAGER"))
            return "redirect:/manager_user";
        else
            return "redirect:/stores/list";
    }




}
