package com.example.diplomnabe.Services;
import com.example.diplomnabe.Classes.Recipe;

import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.Repositories.RecipeRepository;
import com.example.diplomnabe.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecipeService
{
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeProductService recipeProductService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, RecipeProductService recipeProductService)
    {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeProductService = recipeProductService;
    }

    public Page<RecipeDTO> getRecipesDTO(Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);
        return recipes.map(recipe -> recipe.convertRecipeToRecipeDTO());
    }

    public Page<RecipeDTO> getRecipesDTOBySearch(Pageable pageable, String searchedWord, String type)
    {
        Page<Recipe> recipes;
        if(Objects.equals(type, "None") && !Objects.equals(searchedWord, ""))
            recipes = recipeRepository.findByNameWithPagination(pageable,searchedWord);
        else
            if(!Objects.equals(type, "None") && Objects.equals(searchedWord, ""))
                recipes = recipeRepository.findByTypeWithPagination(pageable,type);
            else
                recipes = recipeRepository.findByTypeAndNameWithPagination(pageable,searchedWord,type);
        return recipes.map(recipe -> recipe.convertRecipeToRecipeDTO());
    }

    public void createRecipe(RecipeDTO recipeDTO,Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();

        Recipe recipe = new Recipe(recipeDTO.getName(),recipeDTO.getDescription(),recipeDTO.getTime_for_cooking(),recipeDTO.getType(),user);
        recipeRepository.save(recipe);

        recipeProductService.createRecipeProductWithProductList(recipe.getId(),recipeDTO.getProducts());

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


    public long getPagesCntOfRecipes()
    {
        int cnt = recipeRepository.countOfAllRecipes();
        if(cnt % 3 == 0)
            return (cnt / 3) - 1;
        else
            return cnt / 3;
    }

    public long getPagesCntOfSearchedWord(String searchedWord, String type)
    {
        int cnt = 0;
        if(Objects.equals(searchedWord, "") && !Objects.equals(type, "None"))
            cnt = recipeRepository.countOfTypeMatchingRecipes(type);
        else
            if(!Objects.equals(searchedWord, "") && Objects.equals(type, "None"))
                cnt = recipeRepository.countOfSearchedWordMatchingRecipes(searchedWord);
            else
                cnt = recipeRepository.countOfMatchingRecipes(searchedWord,type);

        if(cnt % 3 == 0)
            return (cnt / 3) - 1;
        else
            return cnt / 3;
    }

    public RecipeDTO getRecipeById(Long recipeId)
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.get();
        return recipe.convertRecipeToRecipeDTO();
    }

    public Recipe updateLikes(Long id, int likes) {
        Recipe recipe = recipeRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));

        recipe.setLikes(likes);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long RecipeId) throws Exception {
        if(recipeRepository.existsById(RecipeId))
            recipeRepository.deleteById(RecipeId);
        else
            throw new Exception("recipe with id " + RecipeId + " does not exists");
    }
    public void deleteFavConnection(Long userId, Long recipeId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + recipeId));

        recipeRepository.deleteFavConnection(userId,recipeId);
    }

    public List<ProductGramsDTO> getRecipeProducts(Long recipeId)
    {
        return recipeRepository.findProductGramsDTOByRecipeId(recipeId);
    }

    public boolean getFavConnectionExist(Long recipeId, Long userId)
    {
        int cnt = recipeRepository.cntFavConnection(userId,recipeId);
        return cnt > 0;
    }

    public boolean getLikeConnectionExist(Long recipeId, Long userId)
    {
        int cnt = recipeRepository.cntLikeConnection(userId,recipeId);
        return cnt > 0;
    }

    public void UserLikeToRecipe(Long recipeId, Long userId)
    {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        User user = userOptional.get();
        Recipe recipe = recipeOptional.get();

        user.getLiked_recipes().add(recipe);
        userRepository.save(user);
        recipe.getUsers_likes().add(user);
        recipeRepository.save(recipe);
    }

    public void deleteLikeConnection(Long userId, Long recipeId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + recipeId));

        recipeRepository.deleteLikeConnection(userId,recipeId);
    }


}
