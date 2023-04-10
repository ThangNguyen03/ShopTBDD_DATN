package com.shoptbdddatn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Product;
import com.shoptbdddatn.model.ProductLine;
import com.shoptbdddatn.repository.ICommentRepository;
import com.shoptbdddatn.repository.IOrderDetailRepository;
import com.shoptbdddatn.repository.IProductLineRepository;
import com.shoptbdddatn.repository.IProductRepository;

@CrossOrigin
@RestController
public class ProductController {
    @Autowired
    IProductRepository productRepository;

    @Autowired
    IProductLineRepository productLineRepository;
    
    @Autowired
    IOrderDetailRepository orderDetailRepository;
    
    @Autowired
    ICommentRepository commentRepository;

    //Lay tat cac san pham
    @GetMapping("/products/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //Lay tat ca cac danh muc san pham
    @GetMapping("/productLine/all")
    public List<ProductLine> getAllProductLine() {
        return productLineRepository.findAll();
    }
    //Lay san pham ban chay nhat
    @GetMapping("/topSellProduct")
    public ResponseEntity<Object>getTopSellProduct(){
        try {
            return new ResponseEntity<>(orderDetailRepository.getTopSellProducts(),HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(e.getCause().getCause().getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
