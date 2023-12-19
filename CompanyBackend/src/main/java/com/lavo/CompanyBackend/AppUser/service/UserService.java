package com.lavo.CompanyBackend.AppUser.service;

import com.lavo.CompanyBackend.AppUser.controller.AuthController;
import com.lavo.CompanyBackend.AppUser.dto.CreateAccountRequest;
import com.lavo.CompanyBackend.AppUser.dto.DetailsUserRequest;
import com.lavo.CompanyBackend.AppUser.dto.UserConvertDetailsUserRequest;
import com.lavo.CompanyBackend.AppUser.exception.UserNotFoundException;
import com.lavo.CompanyBackend.AppUser.model.Role;
import com.lavo.CompanyBackend.AppUser.model.User;
import com.lavo.CompanyBackend.AppUser.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserConvertDetailsUserRequest userRequest;

    private Logger logger= LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserConvertDetailsUserRequest userRequest) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRequest = userRequest;
    }

    private Optional<User> getByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return userOptional;
    }

    public User createUser(CreateAccountRequest request) {

        logger.info("createuser Verileri alındı :"+request);

        User newUser = User.builder()
                .firstName(request.getName())
                .lastName(request.getName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .authorities(new HashSet<>(Arrays.asList(Role.ROLE_USER)))
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = getByEmail(email);

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        Set<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
    public DetailsUserRequest getUserDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
                return null;
            }

            String loggedInEmail = authentication.getName();
            Optional<User> userOptional = getByEmail(loggedInEmail);

            User user = userOptional.orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email: " + loggedInEmail));

            DetailsUserRequest userDetails = userRequest.convertToDetailsUserRequest(user);

            return userDetails;
        } catch (Exception e) {
            logger.error("Error getting user details", e);
            throw new RuntimeException("Error getting user details", e);
        }
    }
}
