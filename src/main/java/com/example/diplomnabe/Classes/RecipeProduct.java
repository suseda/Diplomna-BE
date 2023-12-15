package com.example.diplomnabe.Classes;

import com.example.diplomnabe.DTO.RecipeProductDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "RecipeProduct_table")
public class RecipeProduct
{
    @Id
    @SequenceGenerator( name="recipe_product_sequence", sequenceName="recipe_product_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "recipe_product_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String grams;

    public RecipeProduct(){}
    public RecipeProduct(Long id, Recipe recipe, Product product, String grams) {
        this.id = id;
        this.recipe = recipe;
        this.product = product;
        this.grams = grams;
    }

    public RecipeProduct(Recipe recipe, Product product, String grams) {
        this.recipe = recipe;
        this.product = product;
        this.grams = grams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getGrams() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }

    public RecipeProductDTO convertRecipeProductToRecipeProductDTO()
    {
        return new RecipeProductDTO(this.getRecipe().getId(),this.getProduct().getId(),this.getGrams());
    }
}
