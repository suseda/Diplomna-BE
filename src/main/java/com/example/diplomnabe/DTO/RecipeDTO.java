package com.example.diplomnabe.DTO;


import java.util.ArrayList;
import java.util.List;

public class RecipeDTO
{
    private Long id;
    private String name;
    private int likes;
    private String description;
    private Integer time_for_cooking;
    private String type;

    private Long owner_id;

    private List<ProductGramsDTO> products;
    private ArrayList<Long> user_favourites_recipe_ids;

    public RecipeDTO() {}

    public RecipeDTO(Long id,String name, int likes, String description, Integer time_for_cooking, String type, Long owner_id,ArrayList<Long> user_favourites_recipe_ids) {
        this.id = id;
        this.name = name;
        this.likes = likes;
        this.description = description;
        this.time_for_cooking = time_for_cooking;
        this.type = type;
        this.owner_id = owner_id;
        this.user_favourites_recipe_ids = user_favourites_recipe_ids;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTime_for_cooking() {
        return time_for_cooking;
    }

    public void setTime_for_cooking(Integer time_for_cooking) {
        this.time_for_cooking = time_for_cooking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOwner() {
        return owner_id;
    }

    public void setOwner(Long owner_id) {
        this.owner_id = owner_id;
    }

    public List<ProductGramsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductGramsDTO> products) {
        this.products = products;
    }

    public ArrayList<Long> getUser_favourites_recipe_ids() {
        return user_favourites_recipe_ids;
    }

    public void setUser_favourites_recipe_ids(ArrayList<Long> user_favourites_recipe_ids) {
        this.user_favourites_recipe_ids = user_favourites_recipe_ids;
    }
}
