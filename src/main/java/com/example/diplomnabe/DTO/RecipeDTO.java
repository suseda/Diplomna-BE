package com.example.diplomnabe.DTO;


import java.util.ArrayList;

public class RecipeDTO
{
    private Long id;
    private String name;
    private Integer likes;
    private String description;
    private Integer time_for_cooking;
    private String type;

    private Long owner_id;
    private ArrayList<Long> user_favourites_recipe_ids;

    public RecipeDTO() {}

    public RecipeDTO(Long id,String name, Integer likes, String description, Integer time_for_cooking, String type, Long owner_id,ArrayList<Long> user_favourites_recipe_ids) {
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
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
}
