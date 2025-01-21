package com.ecommerce.storejava.service.product;

import com.ecommerce.storejava.model.Product;
import com.ecommerce.storejava.request.AddProductRequest;
import com.ecommerce.storejava.request.ProductUpdateRequest;

import java.util.List;

public interface IproductService {
    Product addProduct(AddProductRequest product);

    Product updateProduct(ProductUpdateRequest request, Long id);

    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByNameAndBrand(String name, String brand);
    Long countProductsByBrandAndName(String name, String brand);
}

