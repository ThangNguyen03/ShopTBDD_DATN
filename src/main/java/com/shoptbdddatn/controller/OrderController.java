package com.shoptbdddatn.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Customer;
import com.shoptbdddatn.model.Order;
import com.shoptbdddatn.repository.ICustomerRepository;
import com.shoptbdddatn.repository.IGetAmmountByDayRange;
import com.shoptbdddatn.repository.IOrderRepository;

@CrossOrigin
@RestController
public class OrderController {
    @Autowired
	IOrderRepository orderRepository;
	
	@Autowired
	ICustomerRepository customerRepository;
	
	//Lay danh sach cac don hang
	@GetMapping("/orders")
	public List<Order> getAllOrder() {
		return orderRepository.findAll();
	}
	
	
    // Loc don hang theo status, orderNumber, customerID 
	@GetMapping("/order/filter")
	public ResponseEntity<List<Order>> filterOrderByString(
			@RequestParam("status") String status,
			@RequestParam("orderNumber") String orderNumber,
			@RequestParam("customerId") String customerId){
		try {
			List<Order> responseFilter = orderRepository.findOrderByString(status, orderNumber, customerId);
			return new ResponseEntity<>(responseFilter, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} 
	
	//Lay du lieu don hang de hien thi theo ngay thang tren bieu do
	@GetMapping("/order/getAmmountByDateRange")
	public ResponseEntity<?> getAmmountByDayRange(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate){
			try {
				ArrayList<IGetAmmountByDayRange> vRessult = orderRepository.getAmmountByDayDateRange(
						new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
						new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
				return new ResponseEntity<>(vRessult, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	// lay du lieu so luong don hang
	@GetMapping("/order/getAmmountByDay")
	public ResponseEntity<Object> getAmmountByDay(){
			try {
				return new ResponseEntity<>(orderRepository.getAmmountByDayDate(), HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	// Nhan du lieu don hang de tra ve danh sach dat hang
	@GetMapping("/order/getOrderByDateBetween")
	public ResponseEntity<List<Order>> getOrderByDateBetween(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate){
		try {
			List<Order> vRessult = orderRepository.findAllByOrderDateBetween(
					new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
					new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
			return new ResponseEntity<>(vRessult, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	// dem tong so don hang 
	@GetMapping("/order/count-total")
	public ResponseEntity<Object> countOrderTotal(){
			try {
				Long countOrderTotal = orderRepository.countTotalOrder();
				return new ResponseEntity<>(countOrderTotal, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	// Dem tong don hang tra ve tong tien
	@GetMapping("/order/sum-total")
	public ResponseEntity<Object> sumTotalAmmount(){
			try {
				Long sumOrderTotal = orderRepository.sumTotalAmmount();
				return new ResponseEntity<>(sumOrderTotal, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	
	
	//Tao mot don hang
	@PostMapping("/order/create/{id}")
	public ResponseEntity<Object> createOrder(@PathVariable("id") int id, @RequestBody Order Order){
		Optional<Customer> customerData = customerRepository.findById(id);
		try {
			if(customerData.isPresent()) {
				String randomString = createRandom_Alphanumeric();
				Order newOrder = new Order();
				newOrder.setOrderDate(Order.getOrderDate());
				newOrder.setRequiredDate(Order.getRequiredDate());
				newOrder.setShippedDate(Order.getShippedDate());
				newOrder.setStatus(Order.getStatus());
				newOrder.setComments(Order.getComments());
				newOrder.setAmmount(Order.getAmmount());
				newOrder.setCheckNumber(randomString);
				
				Customer customer = customerData.get();
				newOrder.setCustomer(customer);
				Order createOrder = orderRepository.save(newOrder);
				return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return ResponseEntity.unprocessableEntity().body("Fail to create order");
		}
		
	}
	
	//Sua don hang
	@PutMapping("/order/update/{orderId}")
	public ResponseEntity<Object> updateOrder(
			@PathVariable("orderId") Integer orderId,
			@RequestBody Order Order){
		try {
			Optional<Order> orderData = orderRepository.findById(orderId);
			if(orderData.isPresent()) {
				Order newOrder = orderData.get();
				newOrder.setAmmount(Order.getAmmount());
				newOrder.setCheckNumber(Order.getCheckNumber());
				newOrder.setComments(Order.getComments());
				newOrder.setOrderDate(Order.getOrderDate());
				newOrder.setRequiredDate(Order.getRequiredDate());
				newOrder.setShippedDate(Order.getShippedDate());
				newOrder.setStatus(Order.getStatus());
				
				Order saveOrder = orderRepository.save(newOrder);
				return new ResponseEntity<>(saveOrder, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Xoa don hang
	@DeleteMapping("/order/delete/{orderId}")
	public ResponseEntity<Object> deleteOrder(@PathVariable("orderId") Integer orderId){
		try {
			orderRepository.deleteById(orderId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Tao chuoi ngau nhien de them truong check_number trong data order
	public String createRandom_Alphanumeric() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return generatedString;
	    
	}
	
}
