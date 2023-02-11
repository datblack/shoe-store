package com.pro2111.restcontrollers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.entities.Cart;
import com.pro2111.entities.User;
import com.pro2111.service.CartService;

@CrossOrigin("*")
@RestController
@RequestMapping("user/rest/cart")
public class CartRestController {
	@Autowired
	private CartService cartService;

	/**
	 * Thêm một cart.
	 * 
	 * @param cart
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<Cart> create(@RequestBody Cart cart) {
		try {
			return ResponseEntity.ok(cartService.create(cart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tìm kiếm theo user
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("find-by-user/{iduser}")
	public ResponseEntity<List<Cart>> findByUser(@PathVariable("iduser") User user) {
		try {
			return ResponseEntity.ok(cartService.findByUser(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * kiểm tra giỏ hàng của người dùng đã tồn tại hay chưa
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("check-cart-by-user/{iduser}")
	public ResponseEntity<JsonNode> checkCartByUser(@PathVariable("iduser") User user) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			Cart cart = (Cart) cartService.findByUser(user);
			if (cart != null) {
				node.put("value", true);
			} else {
				node.put("value", false);
			}
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật Cart
	 * 
	 * @param id
	 * @param cart
	 * @return
	 */
	@PutMapping("{id}")
	public ResponseEntity<Cart> update(@PathVariable("id") Integer id, @RequestBody Cart cart) {
		try {
			cart.setCartId(id);
			return ResponseEntity.ok(cartService.create(cart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xoá cart
	 * 
	 * @param cart
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Cart> delete(@PathVariable("id") Cart cart) {
		try {
			return ResponseEntity.ok(cartService.delete(cart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy toàn bộ Cart
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<Cart>> getAll() {
		try {
			return ResponseEntity.ok(cartService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Tìm kiếm Cart theo id
	 * 
	 * @param id
	 * @return id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Cart> findById(@PathVariable("id") Integer id) {
		try {
			return ResponseEntity.ok(cartService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
