package com.quizter.service;

import com.quizter.dictionary.CacheType;
import com.quizter.dto.PasswordDto;
import com.quizter.dto.ProfileDto;
import com.quizter.dto.RegistrationUserDto;
import com.quizter.dto.UserEmailDto;
import com.quizter.entity.Credentials;
import com.quizter.entity.Profile;
import com.quizter.entity.User;
import com.quizter.exception.NoUserWithThatIDException;
import com.quizter.exception.TokenException;
import com.quizter.exception.UserIsNotAuthorizedException;
import com.quizter.mapper.UserMapper;
import com.quizter.repository.UserRepository;
import com.quizter.util.AppConstants;
import com.quizter.util.EmailConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

	UserRepository userRepository;

	MailWebService mailWebService;

	TokenService tokenService;

	UserMapper userMapper;

	ValidationService validationService;

	PasswordEncoder passwordEncoder;

	AppConstants appConstants;

	SecurityService securityService;

	public void registerUser(RegistrationUserDto registrationUserDto) {
		validationService.registrationValidation(registrationUserDto);
		User user = userMapper.toUser(registrationUserDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(false);
		userRepository.save(user);
		mailWebService.mailSend(user.getEmail(), EmailConstants.REGISTRATION_SUBJECT, EmailConstants.MAIL_CONTENT_URL,
				appConstants.getHost() + "/active-account?id=" + user.getId() + "&token=" + tokenService.generateToken(user.getEmail(), CacheType.ACTIVATION)
						.getToken());
	}

	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void activateUser(Long id, String token) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			String trueToken = tokenService.getToken(user.get().getEmail(), CacheType.ACTIVATION).getToken();
			if (trueToken == null) {
				userRepository.deleteById(id);
				throw new TokenException("Authentication token expired");
			}
			if (!trueToken.equals(token)) {
				throw new TokenException("Token is wrong");
			}
			tokenService.removeToken(user.get().getEmail(), CacheType.ACTIVATION);
			user.get().setActive(true);
			userRepository.save(user.get());
		} else {
			throw new NoUserWithThatIDException("user", "id", id);
		}
	}

	public void resetPassword(UserEmailDto userEmailDto) {
		String email = userEmailDto.getUserEmail();
		Optional<User> userOptional = findUserByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String token = tokenService.generateToken(email, CacheType.RECOVERY).getToken();
			String appUrl = appConstants.getHost() + "/newPassword?id=" + user.getId() + "&token=" + token;
			mailWebService.mailSend(user.getEmail(), "Restore password", "reset-password-content", appUrl);
		}
	}

	public void saveNewPassword(Long id, String token, PasswordDto passwordDto) {
		if (!securityService.validateResetToken(id, token)) {
			throw new TokenException("Token not valid");
		}
		validationService.passwordValidation(passwordDto);
		User user = userRepository.findById(id).orElseThrow();
		user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
		tokenService.removeToken(user.getEmail(), CacheType.RECOVERY);
		userRepository.save(user);
	}

	public void saveProfile(ProfileDto profileDto) {
		ModelMapper modelMapper = new ModelMapper();
		Profile profile = modelMapper.map(profileDto, Profile.class);
		log.info("ProfileDto = " + profileDto.getFirstName());
		User user = getUserPrincipal().orElseThrow(UserIsNotAuthorizedException::new);
		user.setProfile(profile);
		profile.setUser(user);
		profile.setId(user.getId());
		log.info("Profile = " + profile);
		log.info("User = " + user);
		userRepository.save(user);
	}

	public Optional<User> getUserPrincipal() {
		log.info("Fignia = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		try {
			Credentials credentials = (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userRepository.findByEmail(credentials.getUsername());

		} catch (ClassCastException o_0) {
			return Optional.empty();
		}
	}

}
