package com.cafe.loginhandler;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginHandler extends SimpleUrlAuthenticationSuccessHandler {
	protected void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Authentication authentication) throws IOException {
		String targetURL = determineTargetUrl(authentication);

		if (servletResponse.isCommitted()) {
			return;
		}
		getRedirectStrategy().sendRedirect(servletRequest, servletResponse, targetURL);
		;
	}

	protected String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				return "/admin/test";
			} else if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
				return "/user/test";

			} else if (authority.getAuthority().equals("ROLE_EMPLOYEE")) {
				return "/user/test";
			}
		}
		return "/login";
	}
}
