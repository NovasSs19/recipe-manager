package com.doruk.bartu.recipes.auth;

import com.doruk.bartu.recipes.user.User;
import com.doruk.bartu.recipes.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.badRequest().body(new LoginResponse("Email already taken", null, null));
        }
        User newUser = new User();
        newUser.setEmail(req.email());
        newUser.setPasswordHash(passwordEncoder.encode(req.password()));
        userRepository.save(newUser);

        logger.info("New user registered: {}", req.email()); // Burada log atamasi yapiyoruz
        return ResponseEntity.ok(new LoginResponse("User registered successfully", newUser.getId(), newUser.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(req.email());

        if (userOpt.isEmpty()) {
            logger.warn("Failed login attempt (User not found): {}", req.email());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password", null, null));
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            logger.warn("Failed login attempt (Wrong password): {}", req.email());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid email or password", null, null));
        }


        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, null);
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        logger.info("User logged in successfully: {}", user.getEmail());

        return ResponseEntity.ok(new LoginResponse("Login successful", user.getId(), user.getEmail()));
    }
}