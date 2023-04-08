package com.shoptbdddatn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoptbdddatn.model.Product;

public interface IProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable paging);
	
	@Query(value = "SELECT * FROM products  WHERE product_name LIKE %:name% ORDER BY product_name", nativeQuery = true)
	Page<Product> findProductByProductName(@Param("name") String name, Pageable paging);
	
	@Query(value="SELECT * FROM products WHERE CONCAT(product_name, ' ', product_vendor) LIKE %:string%", nativeQuery = true)
	Page<Product> findProductBySearchString(@Param("string") String stringSearch, Pageable paging);
	
	@Query(value = "SELECT * FROM products WHERE product_vendor LIKE %:brandName% AND product_line_id LIKE %:productLineId% ORDER BY product_name", nativeQuery = true)
	Page<Product> findProductByProductVender_AndProductLine(@Param("brandName") String brandName,@Param("productLineId") String productLineId, Pageable paging);
}
