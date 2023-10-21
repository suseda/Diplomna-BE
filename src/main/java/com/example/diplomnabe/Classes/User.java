package com.example.diplomnabe.Classes;

import com.example.diplomnabe.DTO.UserDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table
public class User
{
    @Id
    @SequenceGenerator( name="user_sequence", sequenceName="user_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
    private List<Recipe> user_recipes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_FAVOURITES_RECIPE_TABLE",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "favourites_recipe_id",referencedColumnName = "id")
    )
    private Set<Recipe> favourites;



    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_recipes = new ArrayList<>();
        this.favourites = new HashSet<>();
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_recipes = new ArrayList<>();
        this.favourites = new HashSet<>();
    }

    public User() {}

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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getUser_recipes() {
        return user_recipes;
    }

    public void setUser_recipes(ArrayList<Recipe> user_recipes) {
        this.user_recipes = user_recipes;
    }

    public Set<Recipe> getFavourites() {
        return favourites;
    }

    public void setFavourites(HashSet<Recipe> favourites) {
        this.favourites = favourites;
    }


    public ArrayList<Long> getUserRecipesId()
    {
        ArrayList<Long> ids = new ArrayList<>();
        for (Recipe userRecipe : this.user_recipes) {
            ids.add(userRecipe.getId());
        }
        return ids;
    }

    public ArrayList<Long> getUserFavouritesRecipesId()
    {
        ArrayList<Long> ids = new ArrayList<>();
        for (Recipe favourite : this.favourites) {
            ids.add(favourite.getId());
        }
        return ids;
    }

    public UserDTO convertUserToUserDTO()
    {
        ArrayList<Long> user_recipes = getUserRecipesId();
        ArrayList<Long> user_favourites = getUserFavouritesRecipesId();
        return new UserDTO(this.getId(), this.getName(), this.getEmail(),user_recipes,user_favourites);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
