package com.shoptbdddatn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoptbdddatn.model.Customer;

public interface ICustomerRepository extends JpaRepository<Customer,Integer> {
    @Query(value="SELECT * FROM customers WHERE first_name LIKE %:fname% ORDER BY first_name DESC", nativeQuery = true)
	List<Customer> findCustomerByFirstNameDesc(@Param("fname") String fname);
	
	@Query(value="SELECT * FROM customers WHERE last_name LIKE %:lname% ORDER BY last_name DESC", nativeQuery = true)
	List<Customer> findCustomerByLastNameDesc(@Param("lname") String lname);
	
	Customer findByPhoneNumber(String phoneNumber);
	
	Customer findByEmail(String email);
	
	@Query(value = "SELECT COUNT(id) FROM customers", nativeQuery = true)
	Long countTotalCustomer();
	
	@Query(value = "SELECT od.id, od.quantity_order\r\n"
			+ "FROM orders o\r\n"
			+ "INNER JOIN order_details od ON o.id = od.order_id\r\n"
			+ "INNER JOIN customers c ON o.customer_id = c.id\r\n"
			+ "WHERE c.email LIKE :customerEmail\r\n"
			+ "AND od.product_id LIKE :productId\r\n"
			+ "GROUP BY o.customer_id", nativeQuery = true)
	Integer checkCustomerBuyProduct(@Param(value = "customerEmail") String customerEmail,@Param(value = "productId") Integer productId);
}
