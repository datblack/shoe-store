package com.pro2111.jwt;

import org.springframework.security.core.Authentication;

public interface AdminAndStaffAuthorizer {
	boolean authorize(Authentication authentication);
}
