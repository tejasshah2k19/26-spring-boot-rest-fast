package com.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	public String generateToken() {

		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuffer token = new StringBuffer();
		for (int i = 1; i <= 10; i++) {
			int x = (int) (Math.random() * data.length());
			token.append(data.charAt(x));
		}

		return token.toString();
	}
}
