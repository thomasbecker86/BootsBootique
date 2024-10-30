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
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


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
        return "index";
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
	return Arrays.asList(BootType.values());
    }

    @PostMapping("/add")
    public String addBoot(Model model, @ModelAttribute("newBoot") Boot newBoot) {
        this.bootRepository.save(newBoot);
        System.out.println(newBoot);
        
        Iterable<Boot> boots = this.bootRepository.findAll();
        model.addAttribute("boots", boots);
        model.addAttribute("listType", BootType.values());
        Boot emptyBoot = new Boot();
        model.addAttribute("newBoot", emptyBoot);
        return "index";
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> bootToDeleteOptional = this.bootRepository.findById(id);
        if (!bootToDeleteOptional.isPresent()) {
            return null;
        }
        Boot bootToDelete = bootToDeleteOptional.get();
        this.bootRepository.delete(bootToDelete);
        return bootToDelete;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        System.out.println("Testing");
        Optional<Boot> bootToIncrementOptional = this.bootRepository.findById(id);
        if (!bootToIncrementOptional.isPresent()) {
            return null;
        }
        Boot bootToIncrement = bootToIncrementOptional.get();
        bootToIncrement.setQuantity(bootToIncrement.getQuantity() + 1);
        return this.bootRepository.save(bootToIncrement);
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootToDecrementOptional = this.bootRepository.findById(id);
        if (!bootToDecrementOptional.isPresent()) {
            return null;
        }
        Boot bootToDecrement = bootToDecrementOptional.get();
        bootToDecrement.setQuantity(bootToDecrement.getQuantity() - 1);
        return this.bootRepository.save(bootToDecrement);
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(Model model, @RequestParam(required = false) String material, @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size, @RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
	/*
        List<Boot> bootList = this.bootRepository.findByMaterial(material);
        model.addAttribute("boots", bootList);
        model.addAttribute("listType", BootType.values());
        Boot emptyBoot = new Boot();
        model.addAttribute("newBoot", emptyBoot);
        return "index";
        */
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, type, size, and minimum
				// quantity
		return this.bootRepository.findByMaterialAndTypeAndSizeAndQuantityGreaterThan(material, type, size, minQuantity);
            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
				// call the repository method that accepts a material, size, and type
                return this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, a type, and a minimum
				// quantity
                return this.bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material, type, minQuantity);
            } else if (Objects.nonNull(type)) {
				// call the repository method that accepts a material and a type
                return this.bootRepository.findByMaterialAndType(material, type);
            } else {
				// call the repository method that accepts only a material
                return this.bootRepository.findByMaterial(material);
            }
	} else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);
            } else if (Objects.nonNull(size)) {
				// call the repository method that accepts a type and a size
		return this.bootRepository.findByTypeAndSize(type, size);
            } else if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type and a minimum quantity
		return this.bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
            } else {
				// call the repository method that accept only a type
                return this.bootRepository.findByType(type);
            }
	} else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a size and a minimum quantity
		return this.bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
            } else {
				// call the repository method that accepts only a size
		return this.bootRepository.findBySize(size);
            }
	} else if (Objects.nonNull(minQuantity)) {
			// call the repository method that accepts only a minimum quantity
            return this.bootRepository.findByQuantityGreaterThan(minQuantity);
	} else {
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
	}
        
    }

    /*
    private Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }
    */
}
