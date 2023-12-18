package com.example.diplomnabe.DTO;

public class RecipeProductDTO
{
    private Long id;
    private Long recipe_id;
    private Long product_id;
    private String grams;

    public RecipeProductDTO(Long recipe_id, Long product_id, String grams) {
        this.recipe_id = recipe_id;
        this.product_id = product_id;
        this.grams = grams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getGrams() {
        return grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }
}
