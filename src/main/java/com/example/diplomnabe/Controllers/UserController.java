package com.example.diplomnabe.Controllers;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/version1/users")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers()
    {
        return userService.getUsersDTO();
    }

    @GetMapping("/recipes/{userId}")
    public List<RecipeDTO> RecipesOfUser(@PathVariable Long userId)
    {
        return userService.getRecipesOfUser(userId);
    }

    @GetMapping("/favourites/{userId}")
    public List<RecipeDTO> FavouritesRecipesOfUser(@PathVariable Long userId)
    {
        return userService.getFavouritesRecipesOfUser(userId);
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user)
    {
        userService.addNewUser(user);
    }
    @PatchMapping("/{userId}/{newName}")
    public void updateName(@PathVariable String newName, @PathVariable Long userId)
    {
        userService.updateUsername(newName,userId);
    }

    @DeleteMapping(path = "/{UserId}")
    public void deleteUser(@PathVariable("UserId") Long UserId) throws Exception {
        userService.deleteUser(UserId);
    }
}
