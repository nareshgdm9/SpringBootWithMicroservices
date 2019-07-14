package com.action.config;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static String[] findLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Object[] array = authorities.toArray();
		String UserRole = String.valueOf(array[0]);
		String[] credentials = { userName, UserRole };
		return credentials;
	}
}
