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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Comment;
import com.shoptbdddatn.model.Customer;
import com.shoptbdddatn.model.Product;
import com.shoptbdddatn.repository.ICommentRepository;
import com.shoptbdddatn.repository.ICustomerRepository;
import com.shoptbdddatn.repository.IProductRepository;

@CrossOrigin
@RestController
public class CommentController {
    @Autowired
	ICommentRepository commentRepository;
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICustomerRepository customerRepository;

    @GetMapping("/comments")
	public List<Comment> getAllComment() {
			return commentRepository.findAllCCommentsDESC();
	}
	
	//lay comment theo customerid
	@GetMapping("/comments/customer/{customerId}")
	public ResponseEntity<?> getCommentByCustomerId(@PathVariable(value="customerId") Integer customerId){
		try {
			return new ResponseEntity<>(commentRepository.findAllByCustomerId(customerId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Chưa có bình luận", HttpStatus.NOT_FOUND);
		}
	}
	
	//lay comment theo productid
	@GetMapping("/comments/product/{productId}")
	public ResponseEntity<?> getCommentByProductId(@PathVariable(value="productId") Integer productId){
		try {
			return new ResponseEntity<>(commentRepository.findAllByProductId(productId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Tao comment id voi quyen ADMIN, CUSTOMER
	@PostMapping("/comment/product/{productId}")
	// @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
	public ResponseEntity<?> createComment(@PathVariable("productId") Integer productId, @RequestBody Comment comment){
		try {
			Optional<Product> productFound = productRepository.findById(productId);
			Optional<Customer> customerFound = Optional.of(customerRepository.findByEmail(comment.getCommentName()));
			if(productFound.isPresent() && customerFound.isPresent()) {
				Comment newComment = new Comment();
				newComment.setCommentName(comment.getCommentName());
				newComment.setCommentDetail(comment.getCommentDetail());
				newComment.setCreateAt(comment.getCreateAt());
				newComment.setVote(comment.getVote());
				newComment.setProduct(productFound.get());
				newComment.setCustomer(customerFound.get());
				
				Comment createComment = commentRepository.save(newComment);
				return new ResponseEntity<>(createComment, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Không tạo được comment", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Không có quyền tạo comment", HttpStatus.BAD_REQUEST);
		}
	}
	
	//Xoa comment theo comment id
	@DeleteMapping("/comment/delete/{commentId}")
	public ResponseEntity<Object> deleteCommentById(@PathVariable("commentId") Integer commentId){
		try {
			commentRepository.deleteById(commentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
