package com.example.diplomnabe.Repositories;

import com.example.diplomnabe.Classes.Product;
import com.example.diplomnabe.Classes.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{

}