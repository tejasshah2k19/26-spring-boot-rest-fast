package com.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.entity.UserEntity;
import com.repository.UserRepository;

public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<UserEntity>> getAllUsers() {

		// token present?
		// token valid db?

		List<UserEntity> allUsers = userRepository.findAll();
//		return ResponseEntity.ok(allUsers);// 200
		return ResponseEntity.status(HttpStatus.OK).body(allUsers);
	}

	// single user -> id

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
		Optional<UserEntity> op = userRepository.findById(userId);
		if (op.isEmpty()) {
			return ResponseEntity.status(404).body(userId);
		} else {
			return ResponseEntity.status(200).body(op.get());
		}
	}

	// delete user by id

}
