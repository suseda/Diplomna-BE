package com.example.diplomnabe.Repositories;

import com.example.diplomnabe.Classes.Product;
import com.example.diplomnabe.Classes.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    @Query(nativeQuery = true,value="SELECT p.id from PRODUCT_TABLE p WHERE name = :product_name")
    long findIdByName(String product_name);
}