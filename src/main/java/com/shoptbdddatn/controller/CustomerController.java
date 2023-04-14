package com.shoptbdddatn.controller;

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

import com.shoptbdddatn.model.Customer;
import com.shoptbdddatn.repository.ICustomerRepository;
import com.shoptbdddatn.repository.IOrderRepository;

@CrossOrigin
@RestController
public class CustomerController {
    @Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	IOrderRepository orderRepository;

	//Lay tat ca cac khach hang
	@GetMapping("/customers")
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}
	
	//Tim khach hang theo id
	@CrossOrigin
	@GetMapping("/customer/details/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer id) {
		Optional<Customer> customerData = customerRepository.findById(id);
		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Tim khach hang theo sdt
	@GetMapping("/customer/details")
	public ResponseEntity<Customer> getCustomerByPhone(@RequestParam("phoneNumber") String phoneNumber){
		try {
			Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
			if(customer != null) {
				return new ResponseEntity<>(customer, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	//Tim khach hang theo email
	@GetMapping("/customer/find")
	public ResponseEntity<Customer> getCustomerByEmail(@RequestParam("email") String email){
		try {
			Customer customer = customerRepository.findByEmail(email);
			if(customer != null) {
				return new ResponseEntity<>(customer, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	// Tim khach hang theo ma don
	@GetMapping("/customer")
	public ResponseEntity<Object> getCustomerByOrderId(@RequestParam("orderId") int orderId){
		try {
			return new ResponseEntity<>(orderRepository.getCustomerByOrderId(orderId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// lay tonng so luong khach hang
	@GetMapping("/customer/count-total")
	public ResponseEntity<Object> countTotalCustomer(){
			try {
				Long countTotalCustomer = customerRepository.countTotalCustomer();
				return new ResponseEntity<>(countTotalCustomer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	//Lay tong tien cua khach hang theo ma kh
	@GetMapping("/customer/sumtotal/{customerId}")
	public ResponseEntity<Object> sumTotalAmmountByCustomerId(@PathVariable("customerId") Integer customerId){
			try {
				Long sumTotalAmmountByCustomer = orderRepository.sumTotalAmmountByCustomerId(customerId);
				return new ResponseEntity<>(sumTotalAmmountByCustomer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

	//Kiem tra xem kh mua san pham chua 
	@GetMapping("customer/{customerEmail}/product/{productId}")
	public ResponseEntity<?> checkCustomerBuyProduct(@PathVariable("customerEmail") String customerEmail,
			@PathVariable("productId") Integer productId){
		try {
			Integer checkCustomerBuyProduct = customerRepository.checkCustomerBuyProduct(customerEmail, productId);
			return new ResponseEntity<>(checkCustomerBuyProduct, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Tao kh hang 
	@PostMapping("/customer/create")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer Customer) {
		try {
			Customer newCustomer = new Customer();
			newCustomer.setFirstName(Customer.getFirstName());
			newCustomer.setAddress(Customer.getAddress());
			newCustomer.setCity(Customer.getCity());
			newCustomer.setEmail(Customer.getEmail());
			newCustomer.setCreditLimit(Customer.getCreditLimit());
			newCustomer.setLastName(Customer.getLastName());
			newCustomer.setOrders(Customer.getOrders());
			newCustomer.setPhoneNumber(Customer.getPhoneNumber());
			Customer createCustomer = customerRepository.save(newCustomer);
			return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("+++++++++++++++++++++::::: "+e.getCause().getCause().getMessage());
			return ResponseEntity.unprocessableEntity().body("Failed to Create Customer: "+e.getCause().getCause().getMessage());
		}
	}
	
	//Sua kh theo ma
	@PutMapping("/customer/update/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable("customerId") Integer id, @RequestBody Customer Customer) {
		Optional<Customer> customerData = customerRepository.findById(id);
		if (customerData.isPresent()) {
			Customer newCustomer = customerData.get();
			newCustomer.setFirstName(Customer.getFirstName());
			newCustomer.setAddress(Customer.getAddress());
			newCustomer.setCity(Customer.getCity());
			newCustomer.setEmail(Customer.getEmail());
			newCustomer.setCreditLimit(Customer.getCreditLimit());
			newCustomer.setLastName(Customer.getLastName());
			newCustomer.setPhoneNumber(Customer.getPhoneNumber());

			Customer savedCustomer = customerRepository.save(newCustomer);
			return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Xoa kh theo ma
	@DeleteMapping("/customer/delete/{customerId}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable("customerId") Integer id) {
		try {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Tim kiem kh theo ten
	@GetMapping("/customer/namelike/{fname}")
	public List<Customer> getCustomerByNameLike(@PathVariable("fname") String customerName){
		try {
			
			List<Customer> fcustomer = customerRepository.findCustomerByFirstNameDesc(customerName);			
			if (fcustomer != null) {
				return fcustomer;
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
