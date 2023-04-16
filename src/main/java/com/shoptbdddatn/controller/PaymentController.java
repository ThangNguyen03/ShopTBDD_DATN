package com.shoptbdddatn.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Order;
import com.shoptbdddatn.model.Payment;
import com.shoptbdddatn.repository.IOrderRepository;
import com.shoptbdddatn.repository.IPaymentRepository;

@CrossOrigin
@RestController
public class PaymentController {
	@Autowired
	IPaymentRepository paymentRepository;
	
	@Autowired
	IOrderRepository orderRepository;
	
    //Lay danh sach thanh toan
	@GetMapping("/payment/all")
	public List<Payment> getAllPayment() {
		return paymentRepository.findAll();
	}
	
    //Sua danh sach thanh toan
	@PostMapping("/payment/create/{orderId}")
	public ResponseEntity<Object> createOrderDetail(
			@PathVariable("orderId") int orderId,
			@RequestBody Payment payment){
		Optional<Order> orderData = orderRepository.findById(orderId);
		try {
			if(orderData.isPresent()) {
				String randomString = createRandom_Alphanumeric();
				Payment newPayment = new Payment();
				newPayment.setAmmount(payment.getAmmount());
				newPayment.setCheckNumber(randomString);
				newPayment.setPaymentDate(payment.getPaymentDate());
				
				Order order = orderData.get();
				newPayment.setOrder(order);

				Payment createPayment = paymentRepository.save(newPayment);
				return new ResponseEntity<>(createPayment, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return ResponseEntity.unprocessableEntity().body("Fail to create order");
		}
		
	}
	
    
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
