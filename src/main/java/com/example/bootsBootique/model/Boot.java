/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bootsBootique.model;

/**
 *
 * @author Thomas
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import com.example.bootsBootique.enums.BootType;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BOOTS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Boot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  @NotNull
  private BootType type;

  @Column(name = "SIZE")
  @NotNull
  private Float size;

  @Column(name = "QUANTITY")
  @Min(1)
  @Max(999)
  private Integer quantity;

  @Column(name = "MATERIAL")
  @Pattern(regexp = "[A-Z a-z]{1,20}")
  private String material;
}