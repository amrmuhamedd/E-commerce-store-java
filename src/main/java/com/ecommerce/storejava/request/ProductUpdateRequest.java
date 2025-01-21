package com.ecommerce.storejava.request;


import com.ecommerce.storejava.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private  int inventory;
    private Category category;
}
