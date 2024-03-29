package com.lavo.CompanyBackend.AppUser.service;

import com.lavo.CompanyBackend.AppUser.requestDto.CreateAccountRequest;
import com.lavo.CompanyBackend.AppUser.responsesDto.DetailsAllUsersResponse;
import com.lavo.CompanyBackend.AppUser.responsesDto.DetailsUserResponse;
import com.lavo.CompanyBackend.AppUser.requestDto.UserConvertDetailsUserRequest;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .phoneNo(request.getPhoneNo())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .createdDate(LocalDateTime.now())
                .authorities(new HashSet<>(Arrays.asList(Role.ROLE_ADMIN)))
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = getByEmail(email);

        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);

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
    public DetailsUserResponse getUserDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
                return null;
            }

            String loggedInEmail = authentication.getName();
            Optional<User> userOptional = getByEmail(loggedInEmail);

            User user = userOptional.orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email: " + loggedInEmail));

            DetailsUserResponse userDetails = userRequest.convertToDetailsUserRequest(user);

            return userDetails;
        } catch (Exception e) {
            logger.error("Error getting user details", e);
            throw new RuntimeException("Error getting user details", e);
        }
    }

    public List<DetailsAllUsersResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            throw new UserNotFoundException("Users Not Found Exceptions");
        }

        List<DetailsAllUsersResponse> responseList = new ArrayList<>();

        for (User user : users) {
            DetailsAllUsersResponse userDetails = userToDetailsAllUsersResponseMapper(user);
            responseList.add(userDetails);
        }

        return responseList;
    }

    private DetailsAllUsersResponse userToDetailsAllUsersResponseMapper(User user){

        if (user.getLastLoginDate()==null){
            return DetailsAllUsersResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstName())
                    .lastname(user.getLastName())
                    .phoneNo(user.getPhoneNo())
                    .email(user.getEmail())
                    .createdDate(getCreatedDateText(user.getCreatedDate()))
                    .roles(user.getAuthorities())
                    .build();
        }
        return DetailsAllUsersResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .phoneNo(user.getPhoneNo())
                .email(user.getEmail())
                .createdDate(getCreatedDateText(user.getCreatedDate()))
                .lastLoginDate(getLastLoginText(user.getLastLoginDate()))
                .roles(user.getAuthorities())
                .build();
    }
        private String getLastLoginText(LocalDateTime lastLoginDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'Saat:' HH:mm");
            String lastSeen = lastLoginDate.format(formatter);
            return  lastSeen;
        }
        private String getCreatedDateText(LocalDateTime createdDate){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String createdTime = createdDate.format(formatter);
            return createdTime;
        }

}
