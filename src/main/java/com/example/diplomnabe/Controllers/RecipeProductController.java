package com.example.diplomnabe.Controllers;

import com.example.diplomnabe.Classes.RecipeProduct;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import com.example.diplomnabe.DTO.RecipeProductDTO;
import com.example.diplomnabe.Services.RecipeProductService;
import com.example.diplomnabe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/version1/recipe_product")
public class RecipeProductController
{
    private final RecipeProductService recipeProductService;

    @Autowired
    public RecipeProductController(RecipeProductService recipeProductService)
    {
        this.recipeProductService = recipeProductService;
    }

    @GetMapping()
    public List<RecipeProductDTO> getRecipeProduct()
    {
        return recipeProductService.getRecipeProductDTO();
    }

    @PostMapping("/{recipeId}/{productId}/{grams}")
    public void createRecipeProduct(@PathVariable Long recipeId, @PathVariable Long productId, @PathVariable String grams)
    {
        recipeProductService.createRecipeProduct(recipeId,productId,grams);
    }
    @PostMapping("/{recipeId}")
    public void createRecipeProductWithProductList(@PathVariable Long recipeId,@RequestBody List<ProductGramsDTO> products)
    {
        recipeProductService.createRecipeProductWithProductList(recipeId,products);
    }
}
