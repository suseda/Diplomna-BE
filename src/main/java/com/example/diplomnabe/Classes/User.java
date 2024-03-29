package com.example.diplomnabe.Classes;

import com.example.diplomnabe.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Data
@Builder
@Entity
@Table(name = "user_table")
public class User implements UserDetails
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
    private List<Recipe> favourites;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_LIKES_RECIPE_TABLE",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "liked_recipes_id",referencedColumnName = "id")
    )
    private List<Recipe> liked_recipes;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_recipes = new ArrayList<>();
        this.favourites = new ArrayList<>();
        this.liked_recipes = new ArrayList<>();
        this.role = role;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_recipes = new ArrayList<>();
        this.favourites = new ArrayList<>();
        this.liked_recipes = new ArrayList<>();
        this.role = Role.USER;
    }

    public User() {}

    public User(Long id, String name, String email, String password, List<Recipe> user_recipes, List<Recipe> favourites, List<Recipe> liked_recipes,Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_recipes = user_recipes;
        this.favourites = favourites;
        this.liked_recipes = liked_recipes;
        this.role = role;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getUser_recipes()
    {
        return user_recipes;
    }

    public void setUser_recipes(ArrayList<Recipe> user_recipes) {
        this.user_recipes = user_recipes;
    }

    public List<Recipe> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Recipe> favourites) {
        this.favourites = favourites;
    }

    public void setUser_recipes(List<Recipe> user_recipes) {
        this.user_recipes = user_recipes;
    }

    public void setFavourites(List<Recipe> favourites) {
        this.favourites = favourites;
    }

    public List<Recipe> getLiked_recipes() {
        return liked_recipes;
    }

    public void setLiked_recipes(List<Recipe> liked_recipes) {
        this.liked_recipes = liked_recipes;
    }

    public ArrayList<Long> getUserRecipesId()
    {
        ArrayList<Long> ids = new ArrayList<>();
        if(this.user_recipes.isEmpty())
            return ids;

        for (Recipe userRecipe : this.user_recipes) {
            ids.add(userRecipe.getId());
        }
        return ids;
    }

    public ArrayList<Long> getUserFavouritesRecipesId()
    {
        ArrayList<Long> ids = new ArrayList<>();
        if(this.favourites.isEmpty())
            return ids;

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
