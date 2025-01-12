package com.ecommerce.storejava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String fileName;
private String filePath;
private String fileType;

@ManyToOne
@JoinColumn(name = "product_id")
private Product product;
}