package com.lavo.CompanyBackend.AppUser.controller;
import com.lavo.CompanyBackend.AppUser.dto.ChangePasswordRequest;
import com.lavo.CompanyBackend.AppUser.dto.CreateAccountRequest;
import com.lavo.CompanyBackend.AppUser.dto.DetailsUserRequest;
import com.lavo.CompanyBackend.AppUser.dto.LoginUserRequest;
import com.lavo.CompanyBackend.AppUser.exception.LoginException;
import com.lavo.CompanyBackend.AppUser.exception.SignUpException;
import com.lavo.CompanyBackend.AppUser.service.JwtService;
import com.lavo.CompanyBackend.AppUser.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        try {
            userService.createUser(createAccountRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (SignUpException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserRequest.getEmail(),
                            loginUserRequest.getPassword()
                    )
            );
            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtService.generateToken(userDetails.getUsername());

                logger.info("kullanici token:"+ token);

                logger.info("Giriş yapan kullanıcı bilgi: " + authentication);
                return ResponseEntity.ok("User logged in successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Authentication failed");
            }
        } catch (LoginException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        try {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during logout", e);
            return new ResponseEntity<>("Error during logout", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok("Password changed successfully");
    }


    @GetMapping("/getuserinfo")
    public ResponseEntity<String> getUserInfo(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring(7);
                String username = jwtService.extractUser(token);
                DetailsUserRequest user = userService.getUserDetails();
                return ResponseEntity.ok("User info: " + username+"  :"+user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed:"+authorizationHeader);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
