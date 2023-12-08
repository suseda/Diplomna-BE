package com.example.diplomnabe.Services;
import com.example.diplomnabe.Classes.Recipe;

import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.Repositories.RecipeRepository;
import com.example.diplomnabe.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Recipe> getRecipes(String search_word)
    {
        Pageable pageable = PageRequest.of(0, 3);
        return recipeRepository.findByNameWithPagination(search_word, pageable).getContent();
        //return recipeRepository.findAll();
    }

    public List<RecipeDTO> getRecipesDTO(String search_word)
    {
        List<RecipeDTO> list_of_DTO = new ArrayList<>();
        List<Recipe> recipes = getRecipes(search_word);
        for (Recipe recipe : recipes) {
            list_of_DTO.add(recipe.convertRecipeToRecipeDTO());
        }
        return list_of_DTO;
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
