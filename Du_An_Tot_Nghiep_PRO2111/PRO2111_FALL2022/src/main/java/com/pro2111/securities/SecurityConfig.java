package com.pro2111.securities;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pro2111.jwt.JwtTokenFilter;
import com.pro2111.jwt.JwtTokenUtil;
import com.pro2111.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	JwtTokenFilter jwtTokenFilter;

	// Cung cấp nguồn dữ liệu đăng nhập
	@Bean
	protected AuthenticationManager configureManager(AuthenticationConfiguration auth, HttpSecurity http)
			throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(username -> userService.findByEmail(username));
		return authenticationManagerBuilder.build();
	}

	// Phân quyền xử dụng
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.csrf().disable();
		http.authorizeRequests().and().cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().xssProtection().and().contentSecurityPolicy("script-src 'self'");

		http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		});

//		http.authorizeRequests().anyRequest().permitAll();
		http.authorizeRequests().antMatchers("/auth/login").permitAll()
		        .antMatchers("/admin/rest/pdf/**").permitAll()
		        .antMatchers("/admin/rest/excel/export-template").permitAll()
		        .antMatchers("/admin/rest/settings/get-district-id-store").permitAll()
				.antMatchers("/admin/rest/image/find-by-product/**").permitAll()
				.antMatchers("/admin/rest/image/find-by-product-origin/**").permitAll()
				.antMatchers("/admin/rest/genarate-QRCode/download-QRCode/**").permitAll()
				.antMatchers("/user/rest/customer/check-email/**").permitAll()
				.antMatchers("/admin/rest/**").authenticated()
				.antMatchers("/user/rest/bills/update").authenticated()
				.antMatchers("/user/rest/bills/find-bill-return-by-user/**").authenticated()
				.antMatchers("/user/rest/customer/update-address/**").authenticated()
				.antMatchers(HttpMethod.GET, "/user/rest/customer/**").authenticated()
				.antMatchers("/user/rest/billdetails/**").authenticated();

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.httpBasic();

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*");
			}
		};
	}

}
