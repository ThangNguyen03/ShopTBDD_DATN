package com.shoptbdddatn.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoptbdddatn.model.Token;
import com.shoptbdddatn.model.User;
import com.shoptbdddatn.repository.UserRepository;
import com.shoptbdddatn.security.JwtUtil;
import com.shoptbdddatn.security.UserPrincipal;
import com.shoptbdddatn.service.TokenService;
import com.shoptbdddatn.service.UserService;


@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private TokenService verificationTokenService;
    
    @Autowired
    private UserRepository userRepsitory;
    
   //Dang ky
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
    	try {
    			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                User newUser =  userService.createUser(user);
                return new ResponseEntity<>(newUser, HttpStatus.OK);
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã được dùng để đăng ký");
    	}
	
    }
    
   //Dang nhap
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        return ResponseEntity.ok(token.getToken());
    }
    
	//Xac thuc nguoi dung hien tai
    @GetMapping("/auth/user/me")
    public ResponseEntity<?> AuthUserByToken(HttpServletRequest request){
    	try {
        	final String authorizationHeader = request.getHeader("Authorization");

            UserPrincipal user = null;
            Token token = null;
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token ")) {
                String jwt = authorizationHeader.substring(6);
                user = jwtUtil.getUserFromToken(jwt);
                token = verificationTokenService.findByToken(jwt);
                if(user == null) {
                	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
    	}

    }
    
    //Hien thi cac user
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUserData(){
    	try {
    		return new ResponseEntity<>(userRepsitory.findAll(), HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
    	}
    }
    
    //Sua vai tro khach hang
    @PostMapping("/user/{userId}/roleCustomer")
    public ResponseEntity<String> setRoleUser(@PathVariable("userId") Long userId){
    	try {
    		Long roleId = (long) 2; //roleId = 2 = ROLE_CUSTOMER;
    		Optional<User> user = userRepsitory.findById(userId);
    		if(user.isPresent()) {
    			userRepsitory.setRoleUser(userId, roleId);
    			return ResponseEntity.status(HttpStatus.OK).body("đã sua quyền cho user thành công");
    		}
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy user");
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
    	}
    }
    
   //Sua vai tro nhan vien
    @PostMapping("/user/{userId}/roleEmployee")
    public ResponseEntity<String> setEmployeeUser(@PathVariable("userId") Long userId){
    	try {
    		Long roleId = (long) 4; //roleId = 4 = ROLE_EMPLOYEE;
    		Optional<User> user = userRepsitory.findById(userId);
    		if(user.isPresent()) {
    			userRepsitory.setRoleUser(userId, roleId);
    			return ResponseEntity.status(HttpStatus.OK).body("đã sua quyền cho user thành công");
    		}
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy user");
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
    	}
    }
    
   //Sua quyen user
    @PutMapping("/user/{userId}/role/{roleId}")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE')")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Long userId, @PathVariable("roleId") Long roleId){
    	try {
    		userRepsitory.updateRoleUser(userId, roleId);
    		return new ResponseEntity<>(HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    //Xoa nguoi dung co quyen USER_DELETE
    @DeleteMapping("/user/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
    	try {
    		userRepsitory.deleteById(userId);
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}catch(Exception e) {
    		return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.NOT_FOUND);
    	}
    }
    
    
    /*
     * TEST
     */
    
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('USER_CREATE', 'USER_UPDATE')")
	

    public ResponseEntity hello() {
        return ResponseEntity.ok("hello  have USER_READ OR USER_CREATE OR USER_UPDATE oR USER_DELETE");
    }
    @GetMapping("/hello2")
    @PreAuthorize("hasAnyAuthority('USER_READ','USER_DELETE')")

    public ResponseEntity hello2() {
        return ResponseEntity.ok("hello 2 have USER_READ OR USER_DELETE");
    }
    
    @GetMapping("/hello3")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity hello3() {
        return ResponseEntity.ok("hello cho ADMIN va CUSTOMER");
    }
    
    @GetMapping("/hello4")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity hello4() {
        return ResponseEntity.ok("hello chi cho ADMIN");
    }
    @GetMapping("/hello6")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity hello6() {
        return ResponseEntity.ok("hello chi cho mANAGER");
    }
    @GetMapping("/hello7")
    @PreAuthorize("hasAnyAuthority('USER_DELETE')")

    public ResponseEntity hello7() {
        return ResponseEntity.ok("hello 2 have USER_DELETE");
    }
    @GetMapping("/hello5")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity hello5() {
        return ResponseEntity.ok("hello chi cho ADMIN");
    }
    
}
