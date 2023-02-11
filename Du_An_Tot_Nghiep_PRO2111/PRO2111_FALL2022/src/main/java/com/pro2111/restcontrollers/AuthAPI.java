/**
 * DATN_FALL2022, 2022
 * AuthAPI.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.AuthRequest;
import com.pro2111.beans.AuthResponse;
import com.pro2111.entities.User;
import com.pro2111.jwt.JwtTokenUtil;
import com.pro2111.service.UserService;
import com.pro2111.utils.EncryptUtil;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@CrossOrigin("*")
@RestController
public class AuthAPI {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/auth/admin/login")
	public ResponseEntity<?> loginAdmin(@RequestBody @Valid AuthRequest authRequest) {
		try {
			User userLogin = userService.findByEmail(authRequest.getUsername());
			Boolean checkPass = EncryptUtil.check(authRequest.getPassword(), userLogin.getPassword());
			if (!checkPass) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			User account = (User) authentication.getPrincipal();
			if (account.getRole() < 2) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} else {
				if (account.getStatus() == 1) {
					String accessToken = jwtTokenUtil.generateAccessToken(account);
					AuthResponse response = new AuthResponse(account, accessToken);
					return ResponseEntity.ok(response);
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				}
			}
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/auth/user/login")
	public ResponseEntity<?> loginUser(@RequestBody @Valid AuthRequest authRequest) {
		try {
			User userLogin = userService.findByEmail(authRequest.getUsername());
			Boolean checkPass = EncryptUtil.check(authRequest.getPassword(), userLogin.getPassword());
			if (!checkPass) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			User account = (User) authentication.getPrincipal();
			if (account.getStatus() == 1) {
				String accessToken = jwtTokenUtil.generateAccessToken(account);
				AuthResponse response = new AuthResponse(account, accessToken);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
