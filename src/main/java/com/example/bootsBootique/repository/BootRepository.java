/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.bootsBootique.repository;

/**
 *
 * @author Thomas
 */
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.bootsBootique.enums.BootType;
import com.example.bootsBootique.model.Boot;

public interface BootRepository extends CrudRepository<Boot, Integer> {

  public List<Boot> findBySize(Float size);
  public List<Boot> findByMaterial(String material);
  public List<Boot> findByType(BootType type);
  public List<Boot> findByQuantityGreaterThan(Integer quantity);

  public List<Boot> findByMaterialAndType(String material, BootType type);
  public List<Boot> findByTypeAndSize(BootType type, Float size);
  public List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer quantity);
  public List<Boot> findBySizeAndQuantityGreaterThan(Float size, Integer quantity);

  public List<Boot> findByMaterialAndSizeAndType(String material, Float size, BootType type);
  public List<Boot> findByMaterialAndTypeAndQuantityGreaterThan(String material, BootType type, Integer size);
  public List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size, Integer quantity);

  public List<Boot> findByMaterialAndTypeAndSizeAndQuantityGreaterThan(String material, BootType type, Float size, Integer quantity);
}
