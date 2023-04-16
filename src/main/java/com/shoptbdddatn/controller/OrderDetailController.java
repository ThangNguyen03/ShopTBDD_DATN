package com.shoptbdddatn.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

import com.shoptbdddatn.model.Order;
import com.shoptbdddatn.model.OrderDetail;
import com.shoptbdddatn.model.Product;
import com.shoptbdddatn.repository.IOrderDetailRepository;
import com.shoptbdddatn.repository.IOrderRepository;
import com.shoptbdddatn.repository.IOrderTracking;
import com.shoptbdddatn.repository.IProductRepository;

@CrossOrigin
@RestController
public class OrderDetailController {
    @Autowired
	IOrderDetailRepository orderDetailRepository;
	
	@Autowired
	IOrderRepository orderRepository;
	
	@Autowired
	IProductRepository productRepository;
	
    // lay danh sach don hang chi tiet
	@GetMapping("/order-details")
	public List<OrderDetail> getAllOrder() {
		return orderDetailRepository.findAll();
	}
	// lay don hang chi tiet qua ma don hang 
	@GetMapping("/order-detail")
	public ResponseEntity<Object> getOrderDetailByOrderId(@RequestParam("orderId") int orderId){
		try {
			return new ResponseEntity<>(orderDetailRepository.getOrderDetailByOrderIds(orderId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Theo doi don hang qua email va ordernumber
	@GetMapping("/order-detail/tracking")
	public ResponseEntity<Object> getOrderInfoByEmailAndOrderNumber(@RequestParam("orderNumber") String orderNumber, @RequestParam("customerEmail") String customerEmail)
	{
		try {
			ArrayList<IOrderTracking> response = orderDetailRepository.getOrderInfoByEmailAndOrderNumber(orderNumber, customerEmail);
			if (response.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}else {
				return new ResponseEntity<>(response, HttpStatus.OK);
			}	
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
    // Them moi don hang chi tiet theo ma don hang tong va ma san pham
	@PostMapping("/order-detail/create/{orderid}/product/{producid}")
	public ResponseEntity<Object> createOrderDetail(
			@PathVariable("orderid") int orderid,
			@PathVariable("producid") int producid,
			@RequestBody OrderDetail OrderDetail){
		Optional<Order> orderData = orderRepository.findById(orderid);
		Optional<Product> productData = productRepository.findById(producid);
		try {
			if(orderData.isPresent()) {
				OrderDetail newOrderDetail = new OrderDetail();
				newOrderDetail.setQuantityOrder(OrderDetail.getQuantityOrder());
				newOrderDetail.setPriceEach(OrderDetail.getPriceEach());
				
				Order order = orderData.get();
				newOrderDetail.setOrder(order);
				
				Product product = productData.get();
				newOrderDetail.setProduct(product);
				OrderDetail createOrderDetail = orderDetailRepository.save(newOrderDetail);
				return new ResponseEntity<>(createOrderDetail, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return ResponseEntity.unprocessableEntity().body("Fail to create order");
		}
		
	}
	
    //Sua don hang chi tiet theo ma don hang chi tiet
	@PutMapping("/order-detail/update/{orderDetailId}")
	public ResponseEntity<Object> updateOrderDetail(
			@PathVariable("orderDetailId") Integer orderDetailId,
			@RequestBody OrderDetail OrderDetail){
		Optional<OrderDetail> orderDetailData = orderDetailRepository.findById(orderDetailId);
		try {
			if(orderDetailData.isPresent()) {
				OrderDetail editOrderDetail = orderDetailData.get();
				editOrderDetail.setQuantityOrder(OrderDetail.getQuantityOrder());
				editOrderDetail.setPriceEach(OrderDetail.getPriceEach());
				OrderDetail saveOrderDetail = orderDetailRepository.save(editOrderDetail);
				return new ResponseEntity<>(saveOrderDetail, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return ResponseEntity.unprocessableEntity().body("Fail to update order detail");
		}
		
	}
	// Xoa don hang chi tiet theo ma don hang chi tiet
	@DeleteMapping("/order-detail/delete/{orderDetailId}")
	public ResponseEntity<Object> deleteOrderDetail(@PathVariable("orderDetailId") int orderDetailId){
		try {
			orderDetailRepository.deleteById(orderDetailId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
