package com.example.diplomnabe.Classes;

import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Recipe
{
    @Id
    @SequenceGenerator( name="recipe_sequence", sequenceName="recipe_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "recipe_sequence")
    private Long id;
    private String name;
    private int likes;
    private String description;
    private Integer time_for_cooking;
    private String type;

    //must add comments

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private User owner;
    @ManyToMany(mappedBy = "favourites",fetch = FetchType.EAGER)
    private List<User> users_favourites;

    @ManyToMany(mappedBy = "liked_recipes",fetch = FetchType.EAGER)
    private List<User> users_likes;

    @OneToMany(mappedBy = "recipe",  fetch = FetchType.EAGER)
    private List<RecipeProduct> products;


    public Recipe(Long id, String name, String description, Integer time_for_cooking, String type,User owner) {
        this.id = id;
        this.name = name;
        this.likes = 0;
        this.description = description;
        this.time_for_cooking = time_for_cooking;
        this.type = type;
        this.users_favourites = new ArrayList<>();
        this.users_likes = new ArrayList<>();
        this.owner = owner;
        this.products = new ArrayList<>();
    }

    public Recipe(String name, String description, Integer time_for_cooking, String type, User owner) {
        this.name = name;
        this.likes = 0;
        this.description = description;
        this.time_for_cooking = time_for_cooking;
        this.type = type;
        this.users_favourites = new ArrayList<>();
        this.users_likes = new ArrayList<>();
        this.owner = owner;
        this.products = new ArrayList<>();
    }

    public Recipe() {}

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers_favourites() {
        return users_favourites;
    }

    public void setUsers_favourites(List<User> users_favourites) {
        this.users_favourites = users_favourites;
    }

    public List<User> getUsers_likes() {
        return users_likes;
    }

    public void setUsers_likes(List<User> users_likes) {
        this.users_likes = users_likes;
    }

    public List<RecipeProduct> getProducts() {
        return products;
    }

    public void setProducts(List<RecipeProduct> products) {
        this.products = products;
    }

    public RecipeDTO convertRecipeToRecipeDTO()
    {
        ArrayList<Long> user_favourites_recipes_ids = getUserFavouritesRecipesId();
        return new RecipeDTO(this.getId(),this.getName(), this.getLikes(),this.getDescription(),this.getTime_for_cooking(),this.getType(),this.getOwner().getId(),user_favourites_recipes_ids);
    }

    private ArrayList<Long> getUserFavouritesRecipesId()
    {
        ArrayList<Long> ids = new ArrayList<>();
        for (User favourite : this.users_favourites) {
            ids.add(favourite.getId());
        }
        return ids;
    }
}
