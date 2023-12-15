package com.example.diplomnabe.Controllers;
import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/pagination")
    public ResponseEntity<List<RecipeDTO>> getRecipes(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 3);
        List<RecipeDTO> recipes = recipeService.getRecipesDTO(pageable).getContent();
        System.out.println(recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/paginationWithSearch")
    public ResponseEntity<List<RecipeDTO>> getRecipesBySearch(@RequestParam int page, @RequestParam String searchedWord) {
        Pageable pageable = PageRequest.of(page, 3);
        List<RecipeDTO> recipes = recipeService.getRecipesDTOBySearch(pageable,searchedWord).getContent();
        System.out.println(recipes);
        return ResponseEntity.ok(recipes);
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
