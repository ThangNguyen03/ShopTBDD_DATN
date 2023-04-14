package com.shoptbdddatn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Product> getAllProduct() {
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
    //lay danh muc san pham
    @GetMapping("/productLine")
    public Optional<ProductLine>getProductLineById(@RequestParam("id") int id){
        return productLineRepository.findById(id);
    }

	//Lay san pham phan trang 
	@GetMapping("/products")
	public ResponseEntity<Map<String, Object>> getAllPorduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size) {
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);
			Page<Product> pageProduct;

			pageProduct = productRepository.findAll(paging);
			products = pageProduct.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);// lay ra danh sach san pham o trang hien tai
			response.put("currentPage", pageProduct.getNumber());// lay ra so trang hien tai
			response.put("totalItems", pageProduct.getTotalElements());// lay ra tong so luong san pham
			response.put("totalPages", pageProduct.getTotalPages());// lay ra tong so trang
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	//Lay san pham theo id
	@GetMapping("/product")
	public ResponseEntity<Product> getProductById(@RequestParam("id") Integer id) {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Loc san pham theo ten va phan trang
	@GetMapping("/products/find")
	public ResponseEntity<Map<String, Object>> getProuctByName(
			@RequestParam("name") String productName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size
			){
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);
			Page<Product> pageProduct;

			pageProduct = productRepository.findProductByProductName(productName, paging);
			products = pageProduct.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProduct.getNumber());
			response.put("totalItems", pageProduct.getTotalElements());
			response.put("totalPages", pageProduct.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Loc san pham theo hang or id loai san pham va phan trang
	@GetMapping("/products/search")
	public ResponseEntity<Map<String, Object>> getProuctByBrandNameAnd_ProductLine(
			@RequestParam("brandName") String brandName,
			@RequestParam("productLineId") String productLineId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size
			){
		try {
			List<Product> products = new ArrayList<Product>();
			Pageable paging = PageRequest.of(page, size);
			Page<Product> pageProduct;

			pageProduct = productRepository.findProductByProductVender_AndProductLine(brandName, productLineId, paging);
			products = pageProduct.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("products", products);
			response.put("currentPage", pageProduct.getNumber());
			response.put("totalItems", pageProduct.getTotalElements());
			response.put("totalPages", pageProduct.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Loc san pham theo id binh luan
	@GetMapping("/product/getByCommentId/{commentId}")
	public ResponseEntity<?> findProductByCommentId(@PathVariable("commentId") Integer commentId){
		try {
			return new ResponseEntity<>(commentRepository.findProductByCommentId(commentId), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	//Tao moi san pham theo id loai san pham
	@PostMapping("/product/create/{productLineId}")
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product Product, @PathVariable("productLineId") int productLineId) {
		Optional<ProductLine> productLineData = productLineRepository.findById(productLineId);
		try {
			if(productLineData.isPresent()) {
				Product newProduct = new Product();
				newProduct.setBuyPrice(Product.getBuyPrice());
				newProduct.setProductCode(Product.getProductCode());
				newProduct.setProductDescription(Product.getProductDescription());
				newProduct.setProductName(Product.getProductName());
				newProduct.setProductVendor(Product.getProductVendor());
				newProduct.setQuantityInStock(Product.getQuantityInStock());
				
				ProductLine productLine = productLineData.get();
				newProduct.setProductLine(productLine);
				Product createProduct = productRepository.save(newProduct);
				return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	//Sua san pham theo id san pham 
	@PutMapping("/product/update/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable("productId") Integer id, @Valid @RequestBody Product Product) {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isPresent()) {
			Product newProduct = productData.get();
			newProduct.setBuyPrice(Product.getBuyPrice());
			newProduct.setProductCode(Product.getProductCode());
			newProduct.setProductDescription(Product.getProductDescription());
			newProduct.setProductName(Product.getProductName());
			newProduct.setProductVendor(Product.getProductVendor());
			newProduct.setQuantityInStock(Product.getQuantityInStock());

			Product savedProduct = productRepository.save(newProduct);
			return new ResponseEntity<>(savedProduct, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//Xoa san pham theo id
	@DeleteMapping("/product/delete/{productId}")
	public ResponseEntity<Object> deleteProductById(@PathVariable("productId") Integer id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
