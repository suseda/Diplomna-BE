package com.example.diplomnabe.Services;

import com.example.diplomnabe.Classes.Product;
import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.DTO.ProductDTO;
import com.example.diplomnabe.Repositories.ProductRepository;
import com.example.diplomnabe.Repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }

    public List<ProductDTO> getProductsDTO()
    {
        List<ProductDTO> list_of_DTO = new ArrayList<>();
        List<Product> products = getProducts();
        for (Product product : products) {
            list_of_DTO.add(product.convertProductToProductDTO());
        }
        return list_of_DTO;
    }

    public void createProduct(String name) {
        if(!productRepository.findByName(name).isEmpty())
        {

        }
        Product product = new Product(name);
        productRepository.save(product);
    }


    public void deleteProduct(Long ProductId) throws Exception {
        if(productRepository.existsById(ProductId))
            productRepository.deleteById(ProductId);
        else
            throw new Exception("product with id " + ProductId + " does not exists");
    }


}
