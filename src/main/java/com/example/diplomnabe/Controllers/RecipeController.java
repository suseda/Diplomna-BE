package com.example.diplomnabe.Controllers;
import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/version1/recipe")
public class RecipeController
{
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @GetMapping("/{searchWord}")
    public List<RecipeDTO> getRecipes(@PathVariable String searchWord)
    {
        return recipeService.getRecipesDTO(searchWord);
    }

    @PostMapping("/{userId}")
    public void createRecipe(@PathVariable Long userId, @RequestBody RecipeDTO recipeDTO) {
        recipeService.createRecipe(recipeDTO,userId);
    }

    @PostMapping("/{userId}/favourites/{recipeId}")
    public void connectUserWithFavouriteRecipe(@PathVariable Long userId, @PathVariable Long recipeId)
    {
        recipeService.favouritesUserToRecipe(recipeId,userId);

    }

    @DeleteMapping(path = "/{RecipeId}")
    public void deleteRecipe(@PathVariable("RecipeId") Long RecipeId) throws Exception {
        recipeService.deleteRecipe(RecipeId);
    }
}
