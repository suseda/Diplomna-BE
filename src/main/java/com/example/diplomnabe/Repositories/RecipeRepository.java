package com.example.diplomnabe.Repositories;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.DTO.ProductGramsDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe,Long>
{
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Recipe> findByNameWithPagination(Pageable pageable, String searchedWord);


    @Query("SELECT r FROM Recipe r WHERE LOWER(r.type) LIKE LOWER(:type)")
    Page<Recipe> findByTypeWithPagination(Pageable pageable,@Param("type") String type);

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.type) LIKE LOWER(:type) AND LOWER(r.name) LIKE LOWER(CONCAT('%', :searchedWord, '%'))")
    Page<Recipe> findByTypeAndNameWithPagination(Pageable pageable,@Param("searchedWord") String searchedWord,@Param("type") String type);

    @Query("SELECT COUNT(*) from Recipe r")
    int countOfAllRecipes();
    @Query("SELECT COUNT(*) from Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    int countOfSearchedWordMatchingRecipes(String searchedWord);

    @Query("SELECT COUNT(*) from Recipe r WHERE LOWER(r.type) LIKE LOWER(:type)")
    int countOfTypeMatchingRecipes(@Param("type")String type);

    @Query("SELECT COUNT(*) FROM Recipe r WHERE LOWER(r.type) LIKE LOWER(:type) AND LOWER(r.name) LIKE LOWER(CONCAT('%', :searchedWord, '%'))")
    int countOfMatchingRecipes(@Param("searchedWord")String searchedWord,@Param("type")String type);

    @Query("SELECT new com.example.diplomnabe.DTO.ProductGramsDTO(product_table.name, recipe_product_table.grams, recipe_product_table.recipe.id) " +
            "FROM Product product_table " +
            "LEFT JOIN RecipeProduct recipe_product_table ON product_table.id = recipe_product_table.product.id " +
            "WHERE recipe_product_table.recipe.id = ?1")
    List<ProductGramsDTO> findProductGramsDTOByRecipeId(Long recipeId);


    @Modifying
    @Query(value = "DELETE FROM user_favourites_recipe_table WHERE user_id = :userId AND favourites_recipe_id = :recipeId", nativeQuery = true)
    void deleteFavConnection(@Param("userId") Long userId, @Param("recipeId") Long recipeId);

    @Modifying
    @Query(value = "DELETE FROM user_likes_recipe_table WHERE user_id = :userId AND liked_recipes_id = :recipeId", nativeQuery = true)
    void deleteLikeConnection(@Param("userId") Long userId, @Param("recipeId") Long recipeId);

    @Query(value = "SELECT COUNT(*) FROM user_favourites_recipe_table WHERE user_id = :userId AND favourites_recipe_id = :recipeId", nativeQuery = true)
    int cntFavConnection(@Param("userId") Long userId, @Param("recipeId") Long recipeId);

    @Query(value = "SELECT COUNT(*) FROM user_likes_recipe_table WHERE user_id = :userId AND liked_recipes_id = :recipeId", nativeQuery = true)
    int cntLikeConnection(@Param("userId") Long userId, @Param("recipeId") Long recipeId);


}
