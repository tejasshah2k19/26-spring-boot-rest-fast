package com.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginDTO;
import com.entity.UserEntity;
import com.repository.UserRepository;
import com.service.TokenService;

@RestController
@RequestMapping("/public/")
public class SessionController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenService tokenService;
	
	@PostMapping("signup")
	public ResponseEntity<?> signup(@RequestBody UserEntity user) {

		// validation
		// 400
		// email - duplicate - already registered

		Optional<UserEntity> op = userRepository.findByEmail(user.getEmail());
		if (op.isPresent()) {
			// already registered
			return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
		} else {
			userRepository.save(user);
			HashMap<String, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("msg", "User Created");
			return ResponseEntity.status(HttpStatus.CREATED).body(map);

		}
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginDTO user) {

		Optional<UserEntity> op = userRepository.findByEmail(user.getEmail());
		if (op.isPresent()) {
			UserEntity dbUser = op.get();
			if (dbUser.getPassword().equals(user.getPassword())) {
				// login done
				String token = tokenService.generateToken(); 
				dbUser.setToken(token);
				userRepository.save(dbUser);
				return ResponseEntity.status(200).body(dbUser);// all user information

			}

		}
		return ResponseEntity.status(401).body(user);// email password
	}

}
