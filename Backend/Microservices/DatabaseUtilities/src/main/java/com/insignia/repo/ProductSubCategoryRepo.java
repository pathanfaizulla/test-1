package com.insignia.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insignia.entity.ProductSubCategory;

public interface ProductSubCategoryRepo extends JpaRepository<ProductSubCategory, Serializable> {

}
