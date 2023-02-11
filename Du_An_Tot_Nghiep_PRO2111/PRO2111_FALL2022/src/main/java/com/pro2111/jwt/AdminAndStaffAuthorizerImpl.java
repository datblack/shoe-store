package com.pro2111.jwt;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pro2111.entities.User;
import com.pro2111.service.UserService;

@Service("appAuthorizer")
public class AdminAndStaffAuthorizerImpl implements AdminAndStaffAuthorizer {
	private final Logger logger = LoggerFactory.getLogger(AdminAndStaffAuthorizerImpl.class);
	@Autowired
	UserService userService;

	@Override
	public boolean authorize(Authentication authentication) {

		boolean isAllow = false;
		try {
			UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
			if (user == null) {
				return isAllow;
			}
			User account = (User) user.getPrincipal();

			if (account.getUserId() == 0) {
				return isAllow;
			}
			if (userService.findById(account.getUserId()).getRole() > 2) {
				isAllow = true;
			}
//			System.out.println(user.getPrincipal());
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
		return isAllow;
	}

	// Lay ra securedPath duoc Annotate RequestMapping trong Controller
	private String extractSecuredPath(Object callerObj) {
		Class<?> clazz = ResolvableType.forClass(callerObj.getClass()).getRawClass();
		Optional<Annotation> annotation = Arrays.asList(clazz.getAnnotations()).stream().filter((ann) -> {
			return ann instanceof RequestMapping;
		}).findFirst();
		logger.debug("FOUND CALLER CLASS: {}", ResolvableType.forClass(callerObj.getClass()).getType().getTypeName());
		if (annotation.isPresent()) {
			return ((RequestMapping) annotation.get()).value()[0];
		}
		return null;
	}

}
