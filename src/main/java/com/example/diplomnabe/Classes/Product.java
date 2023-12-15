package com.example.diplomnabe.Classes;

import com.example.diplomnabe.DTO.ProductDTO;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product_table")
public class Product
{
    @Id
    @SequenceGenerator( name="product_sequence", sequenceName="product_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;
    private String name;


    @OneToMany(mappedBy = "product",  fetch = FetchType.EAGER)
    private List<RecipeProduct> recipes;


    public Product() {}

    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
        this.recipes = new ArrayList<>();
    }

    public Product(String name) {
        this.name = name;
        this.recipes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductDTO convertProductToProductDTO()
    {
        return new ProductDTO(this.getId(), this.getName());
    }
}
