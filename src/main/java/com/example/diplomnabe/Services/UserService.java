package com.example.diplomnabe.Services;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Classes.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public List<UserDTO> getUsersDTO()
    {
        List<UserDTO> list_of_DTO = new ArrayList<>();
        List<User> users = getUsers();
        for (User user : users) {
            list_of_DTO.add(user.convertUserToUserDTO());
        }
        return list_of_DTO;
    }

    public void addNewUser(User user)
    {
        userRepository.save(user);
    }


    public void deleteUser(Long UserId) throws Exception {
        if(userRepository.existsById(UserId))
            userRepository.deleteById(UserId);
        else
            throw new Exception("user with id " + UserId + " does not exists");
    }

    @Transactional
    public List<RecipeDTO> getRecipesOfUser(Long userId)
    {
        User user = userRepository.getReferenceById(userId);
        List<Recipe> recipes = user.getUser_recipes();
        List<RecipeDTO> recipesDTO = new ArrayList<>();
        for(Recipe recipe: recipes)
            recipesDTO.add(recipe.convertRecipeToRecipeDTO());
        return recipesDTO;
    }

    @Transactional
    public List<RecipeDTO> getFavouritesRecipesOfUser(Long userId)
    {
        User user = userRepository.getReferenceById(userId);
        List<Recipe> fav = user.getFavourites();
        List<RecipeDTO> recipesDTO = new ArrayList<>();

        if (fav.isEmpty())
            return recipesDTO;

        for(Recipe recipe: fav)
            recipesDTO.add(recipe.convertRecipeToRecipeDTO());
        return recipesDTO;
    }

    public void updateUsername(String newName, Long userId)
    {
        User user = userRepository.getReferenceById(userId);
        user.setName(newName);
        userRepository.save(user);
    }
}
