package com.lavo.CompanyBackend.AppUser.controller;
import com.lavo.CompanyBackend.AppUser.model.RefreshToken;
import com.lavo.CompanyBackend.AppUser.requestDto.*;
import com.lavo.CompanyBackend.AppUser.exception.SignUpException;
import com.lavo.CompanyBackend.AppUser.responsesDto.AuthResponse;
import com.lavo.CompanyBackend.AppUser.responsesDto.DetailsUserResponse;
import com.lavo.CompanyBackend.AppUser.responsesDto.RefreshTokenResponse;
import com.lavo.CompanyBackend.AppUser.responsesDto.SignUpResponse;
import com.lavo.CompanyBackend.AppUser.service.JwtService;
import com.lavo.CompanyBackend.AppUser.service.RefreshTokenService;
import com.lavo.CompanyBackend.AppUser.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody CreateAccountRequest createAccountRequest) {

        SignUpResponse signUpResponse= new SignUpResponse();
        signUpResponse.setName(createAccountRequest.getName());
        signUpResponse.setEmail(createAccountRequest.getEmail());

        try {
            userService.createUser(createAccountRequest);
            signUpResponse.setMessage("User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
        } catch (SignUpException ex) {
            signUpResponse.setMessage("User registration failed: "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(signUpResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserRequest loginUserRequest) {

        AuthResponse authResponse = new AuthResponse();
        authResponse.setEmail(loginUserRequest.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserRequest.getEmail(),
                            loginUserRequest.getPassword()
                    )
            );

            if (authentication != null && authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String accessToken = jwtService.generateToken(userDetails.getUsername());
                RefreshToken refreshToken =refreshTokenService.createRefreshToken(userDetails.getUsername());

                authResponse.setAccessToken(accessToken);
                authResponse.setMessage("Login Success");
                authResponse.setToken(refreshToken.getToken());

                return ResponseEntity.ok(authResponse);
            } else {
                authResponse.setMessage("Login failed: Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
            }
        } catch (AuthenticationException ex) {
            authResponse.setMessage("Login failed: Incorrect username or password,"+ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
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
    public ResponseEntity<DetailsUserResponse> getUserDetailsFromToken(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DetailsUserResponse());
        }
        try {
            String token = authorizationHeader.substring(7);
            String username = jwtService.extractUser(token);
            DetailsUserResponse user = userService.getUserDetails();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            DetailsUserResponse errorResponse = DetailsUserResponse.builder()
                    .firstname("Authentication failed")
                    .lastname("Authentication failed")
                    .email("Authentication failed: " + e.getMessage())
                    .phoneNo("Authentication failed: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getEmail());
                    return ResponseEntity.ok(RefreshTokenResponse.builder()
                                    .accessToken(accessToken).token(refreshTokenRequest.getToken()).build());
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }


}
