package com.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.entity.UserEntity;
import com.repository.UserRepository;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter implements Filter {

	@Autowired
	UserRepository userRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) (request);
		HttpServletResponse res = (HttpServletResponse) (response);
		String url = req.getRequestURL().toString();
		System.out.println("AuthToken Filter Called");

		if (url.contains("/public/")) {
			chain.doFilter(request, response);// go ahead

		} else {
			//private url 
			String token = req.getHeader("token");
			System.out.println("token => " + token);
			// r07hTgPQw5

			Optional<UserEntity> op = userRepository.findByToken(token);

			if (op.isEmpty()) {
				// go back
				HashMap<String, String> map = new HashMap<>();
				map.put("msg", "Invalid Token");
				res.setContentType("application/json");
				res.setStatus(401);
				res.getWriter().print(map);
			} else {
				chain.doFilter(request, response);// go ahead
			}
		}
	}
}
