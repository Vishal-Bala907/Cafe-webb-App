package com.cafe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.cafe.passEncoDeco.PassEncoDeco;
import com.cafe.services.LoginHandler;
import com.cafe.services.LoginUserService;

@Configuration
public class SecurityConfiguration {

	@Autowired
	LoginHandler loginHandler;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	PassEncoDeco deco;

	@Bean
	SecurityFilterChain chain(HttpSecurity security) throws Exception {
		security.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/CSS/**", "/JavaScript/**", "/Public/**", "/Images/**", "/common/**",
						"/Images/cover/**", "/login", "/signup-page", "/register","/error")
				.permitAll().requestMatchers("admin/**", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/common/**", "user/**", "/api/**", "/empAndCus/**","/foo/**")
				.hasAnyRole("ADMIN", "CUSTOMER", "EMPLOYEE").anyRequest().authenticated())
				.rememberMe(me -> me.tokenValiditySeconds(60 * 60 * 24 * 7));

		security.csrf(csrf -> csrf.disable());
		security.formLogin((form) -> form.loginPage("/login").successHandler(loginHandler).permitAll());
		return security.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(loginUserService).passwordEncoder(deco);
	}
}
