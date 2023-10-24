package com.example.diplomnabe.Services;
import com.example.diplomnabe.Classes.Recipe;

import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.RecipeRepository;
import com.example.diplomnabe.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService
{
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository)
    {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }
    public List<Recipe> getRecipes()
    {
        return recipeRepository.findAll();
    }




    public void createRecipe(RecipeDTO recipeDTO,Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        User user = userOptional.get();

        Recipe recipe = new Recipe(recipeDTO.getName(),recipeDTO.getDescription(),recipeDTO.getTime_for_cooking(),recipeDTO.getType(),user);

        recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long RecipeId) throws Exception {
        if(recipeRepository.existsById(RecipeId))
            recipeRepository.deleteById(RecipeId);
        else
            throw new Exception("recipe with id " + RecipeId + " does not exists");
    }

    public void favouritesUserToRecipe(Long recipeId,Long userId)
    {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        User user = userOptional.get();
        Recipe recipe = recipeOptional.get();

        user.getFavourites().add(recipe);
        userRepository.save(user);
        recipe.getUsers_favourites().add(user);
        recipeRepository.save(recipe);

    }
}
