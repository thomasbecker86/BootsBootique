/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bootsBootique.controller;

/**
 *
 * @author Thomas
 */
import com.example.bootsBootique.exceptions.QueryNotSupportedException;
import com.example.bootsBootique.enums.BootType;
import com.example.bootsBootique.model.Boot;
import com.example.bootsBootique.repository.BootRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/boots")
public class BootController {

    private final BootRepository bootRepository;
    
    public BootController(final BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }
  
    @GetMapping("/")
    public String getAllBoots(Model model) {
        Iterable<Boot> boots = this.bootRepository.findAll();
        model.addAttribute("boots", boots);
        model.addAttribute("listType", BootType.values());
        Boot emptyBoot = new Boot();
        model.addAttribute("newBoot", emptyBoot);
        model.addAttribute("searchBoot", emptyBoot);
        return "index";
    }

    @PostMapping("/add")
    public String addBoot(Model model, @ModelAttribute("newBoot") Boot newBoot) {
        this.bootRepository.save(newBoot);
        System.out.println(newBoot);        
        return "redirect:/boots/";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBoot(Model model, @PathVariable("id") Integer id) throws QueryNotSupportedException {        
        Optional<Boot> bootToDeleteOptional = this.bootRepository.findById(id);
        if (!bootToDeleteOptional.isPresent()) {
            throw new QueryNotSupportedException("Couldn't find a boot with the given id!");
        }
        this.bootRepository.delete(bootToDeleteOptional.get());
        return "redirect:/boots/";
    }    

    @GetMapping("/changequant/{id}/{change}")
    public String changeQuantity(Model model, @PathVariable("id") Integer id, @PathVariable("change") String change) throws QueryNotSupportedException {
        Optional<Boot> bootToChangeQuantityOptional = this.bootRepository.findById(id);
        if (!bootToChangeQuantityOptional.isPresent()) {
            throw new QueryNotSupportedException("Couldn't find a boot with the given id!");
        }
        Boot bootToChangeQuantity = bootToChangeQuantityOptional.get();
        if (change.equalsIgnoreCase("increment")) {
            bootToChangeQuantity.setQuantity(bootToChangeQuantity.getQuantity() + 1);
        } else if (change.equalsIgnoreCase("decrement")) {
            if (bootToChangeQuantity.getQuantity() > 1) {
                bootToChangeQuantity.setQuantity(bootToChangeQuantity.getQuantity() - 1);
            }
        } else {
            throw new QueryNotSupportedException("An error occured whily trying to change the quantity of the boot with id " + id + "!");
        }        
        this.bootRepository.save(bootToChangeQuantity);        
        return "redirect:/boots/";
    }
    
    @GetMapping("/search")
    public String searchBoots(Model model, @ModelAttribute("searchBoot") Boot searchBoot) throws QueryNotSupportedException {
        Iterable<Boot> boots = new ArrayList<>();
        String material = searchBoot.getMaterial();
        Float size = searchBoot.getSize();
        BootType type = searchBoot.getType();
        
        if (!(searchBoot.getMaterial() == null || searchBoot.getMaterial().equals(""))) { 
            if (searchBoot.getType() != null && searchBoot.getSize() != null) {
                boots = this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
            } else if (searchBoot.getType() != null) {
                boots = this.bootRepository.findByMaterialAndType(material, type);
            } else if (searchBoot.getSize()!= null) {
                boots = this.bootRepository.findByMaterialAndSize(material, size);
            } else {
                boots = this.bootRepository.findByMaterial(material);
            }
        } else if (type != null) {
            if (size != null) {
                boots = this.bootRepository.findByTypeAndSize(type, size);
            } else {
                boots = this.bootRepository.findByType(type);
            }
        } else if (size != null) {
            boots = this.bootRepository.findBySize(size);            
        } else {
            throw new QueryNotSupportedException("Something went wrong with your search :/(.");
        }
        model.addAttribute("boots", boots);
        model.addAttribute("listType", BootType.values());
        Boot emptyBoot = new Boot();
        model.addAttribute("newBoot", emptyBoot);
        return "index";
    }
}
