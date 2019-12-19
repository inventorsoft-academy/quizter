package com.quizter.controller;

import com.quizter.entity.User;
import com.quizter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

	UserService userService;

	@GetMapping("/")
	public String defaultPage() {
		log.info("defaultController");
		Optional<User> user = userService.getUserPrincipal();
		if (user.isPresent()) {
			if (user.get().getProfile() == null) {
				return "redirect:/profile/edit";
			}
			switch (user.get().getRole()) {
			case ADMIN:
				return "redirect:/admin";
			case TEACHER:
				return "redirect:/teacher";
			case STUDENT:
				return "redirect:/desk";
			default:
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}
	}
}
