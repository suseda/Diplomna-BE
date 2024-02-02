package com.example.diplomnabe.Services;

import com.example.diplomnabe.Classes.Product;
import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.RecipeProduct;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import com.example.diplomnabe.DTO.RecipeProductDTO;
import com.example.diplomnabe.Repositories.ProductRepository;
import com.example.diplomnabe.Repositories.RecipeProductRepository;
import com.example.diplomnabe.Repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeProductService
{
    private final RecipeProductRepository recipeProductRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RecipeProductService(RecipeProductRepository recipeProductRepository, RecipeRepository recipeRepository, ProductRepository productRepository)
    {
        this.recipeProductRepository = recipeProductRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    public List<RecipeProductDTO> getRecipeProductDTO()
    {
        List<RecipeProductDTO> list_of_DTO = new ArrayList<>();
        List<RecipeProduct> recipe_product = recipeProductRepository.findAll();
        for (RecipeProduct recipeProduct : recipe_product) {
            list_of_DTO.add(recipeProduct.convertRecipeProductToRecipeProductDTO());
        }
        return list_of_DTO;
    }

    public void createRecipeProduct(Long recipeId, Long productId, String grams)
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.get();

        RecipeProduct recipe_product = new RecipeProduct(recipe,product,grams);
        recipeProductRepository.save(recipe_product);
    }

    public void createRecipeProductWithProductList(Long recipeId,List<ProductGramsDTO> products)
    {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.get();
        for(int i = 0; i < products.size(); i++ )
        {
            long id = productRepository.findIdByName(products.get(i).getProductName());
            Optional<Product> productOptional = productRepository.findById(id);
            Product product = productOptional.get();
            RecipeProduct rec_pro = new RecipeProduct(recipe,product,products.get(i).getGrams());
            recipeProductRepository.save(rec_pro);
        }
    }
}
