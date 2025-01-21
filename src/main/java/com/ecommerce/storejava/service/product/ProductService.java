package com.ecommerce.storejava.service.product;

import com.ecommerce.storejava.exceptions.ProductNotFoundException;
import com.ecommerce.storejava.model.Category;
import com.ecommerce.storejava.model.Product;
import com.ecommerce.storejava.repository.CategoryRepository;
import com.ecommerce.storejava.repository.ProductRepository;
import com.ecommerce.storejava.request.AddProductRequest;
import com.ecommerce.storejava.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements  IproductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found
        //if yes set it  as new product category
        //if no then save it
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() ->  {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private  Product createProduct(AddProductRequest request, Category category){
        return  new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }


    @Override
    public Product updateProduct(ProductUpdateRequest request, Long id) {
        return  productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
    }

    private  Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setPrice(request.getPrice());

        Category category= categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, ()-> {throw new ProductNotFoundException("product not found");});
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrandName(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByNameAndBrand(String name, String brand) {
        return productRepository.findByNameAndBrand(name, brand);
    }

    @Override
    public Long countProductsByBrandAndName(String name, String brand) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
