package com.example.diplomnabe.Controllers;
import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Services.RecipeProductService;
import com.example.diplomnabe.Services.RecipeService;
import jakarta.websocket.server.PathParam;
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

    @GetMapping()
    public long getPagesCnt()
    {
        return recipeService.getPagesCntOfRecipes();
    }

    @GetMapping("/recipeId")
    public RecipeDTO getRecipe(@RequestParam Long Id)
    {
        return recipeService.getRecipeById(Id);
    }

    @GetMapping("/getSearchedCnt")
    public long getPagesCntOfSearchedWord(@RequestParam String searchedWord, @RequestParam String type)
    {
        return recipeService.getPagesCntOfSearchedWord(searchedWord,type);
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<RecipeDTO>> getRecipes(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 3);
        List<RecipeDTO> recipes = recipeService.getRecipesDTO(pageable).getContent();
        System.out.println(recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/paginationWithSearch")
    public ResponseEntity<List<RecipeDTO>> getRecipesBySearch(@RequestParam int page, @RequestParam String searchedWord, @RequestParam String type) {
        Pageable pageable = PageRequest.of(page, 3);
        List<RecipeDTO> recipes = recipeService.getRecipesDTOBySearch(pageable,searchedWord,type).getContent();
        System.out.println(recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/recipe-products")
    public List<ProductGramsDTO> getRecipeProducts(@RequestParam Long recipeId)
    {
        return recipeService.getRecipeProducts(recipeId);
    }

    @GetMapping("/favConnectionExist")
    public boolean getFavConnectionExist(@RequestParam Long recipeId,@RequestParam Long userId)
    {
        return recipeService.getFavConnectionExist(recipeId,userId);
    }

    @GetMapping("/likeConnectionExist")
    public boolean getLikeConnectionExist(@RequestParam Long recipeId,@RequestParam Long userId)
    {
        return recipeService.getLikeConnectionExist(recipeId,userId);
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

    @PostMapping("/{userId}/like/{recipeId}")
    public void connectUserWithLikedRecipe(@PathVariable Long userId, @PathVariable Long recipeId)
    {
        recipeService.UserLikeToRecipe(recipeId,userId);
    }

    @PatchMapping("/likes/{recipeId}")
    public ResponseEntity<Recipe> updateLikes(@PathVariable Long recipeId, @RequestParam int likes)
    {
        Recipe recipe = recipeService.updateLikes(recipeId, likes);
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/delete/{userId}/favourites/{recipeId}")
    public void deleteFavouritesConnection(@PathVariable Long userId,@PathVariable Long recipeId)
    {
        recipeService.deleteFavConnection(userId,recipeId);
    }

    @DeleteMapping("/delete/{userId}/like/{recipeId}")
    public void deleteLikeConnection(@PathVariable Long userId,@PathVariable Long recipeId)
    {
        recipeService.deleteLikeConnection(userId,recipeId);
    }
    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId) throws Exception {
        recipeService.deleteRecipe(recipeId);
    }
}
