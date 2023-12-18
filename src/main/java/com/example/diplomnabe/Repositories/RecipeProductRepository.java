package com.example.diplomnabe.Repositories;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductRepository extends JpaRepository<RecipeProduct,Long>
{

}
