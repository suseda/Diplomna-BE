package com.example.diplomnabe.Controllers;

import com.example.diplomnabe.Classes.Product;
import com.example.diplomnabe.DTO.ProductDTO;
import com.example.diplomnabe.DTO.RecipeDTO;
import com.example.diplomnabe.Services.ProductService;
import com.example.diplomnabe.Services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/version1/product")
public class ProductController
{
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductDTO> getProducts()
    {
        return productService.getProductsDTO();
    }

    @PostMapping("/{name}")
    public void createProduct(@PathVariable String name) {
        productService.createProduct(name);
    }

    @DeleteMapping(path = "/{productId}")
    public void deleteProduct(@PathVariable("productId") Long ProductId) throws Exception {
        productService.deleteProduct(ProductId);
    }
}

