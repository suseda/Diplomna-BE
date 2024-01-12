package com.example.diplomnabe.Repositories;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long>
{
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Recipe> findByNameWithPagination(Pageable pageable, String searchedWord);

    @Query("SELECT COUNT(*) from Recipe r")
    long countOfAllRecipes();
    @Query("SELECT COUNT(r) from Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    long countOfMatchingRecipes(String searchedWord);

    @Query("SELECT new com.example.diplomnabe.DTO.ProductGramsDTO(product_table.name, recipe_product_table.grams, recipe_product_table.recipe.id) " +
            "FROM Product product_table " +
            "LEFT JOIN RecipeProduct recipe_product_table ON product_table.id = recipe_product_table.product.id " +
            "WHERE recipe_product_table.recipe.id = ?1")
    List<ProductGramsDTO> findProductGramsDTOByRecipeId(Long recipeId);

}
