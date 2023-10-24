package com.example.diplomnabe.DTO;

import java.util.ArrayList;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    private ArrayList<Long> recipe_ids;
    private ArrayList<Long> favourites_recipe_ids;


    public UserDTO(Long id,String name, String email ,ArrayList<Long> recipe_ids, ArrayList<Long> favourites_recipe_ids) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.recipe_ids = recipe_ids;
        this.favourites_recipe_ids = favourites_recipe_ids;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}