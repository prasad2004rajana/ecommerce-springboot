package in.ecommerce.ecommerce_backend.service;

import in.ecommerce.ecommerce_backend.dto.auth.AuthResponse;
import in.ecommerce.ecommerce_backend.dto.auth.LoginRequest;
import in.ecommerce.ecommerce_backend.dto.auth.RegisterRequest;
import in.ecommerce.ecommerce_backend.entity.Role;
import in.ecommerce.ecommerce_backend.entity.User;
import in.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import in.ecommerce.ecommerce_backend.repository.UserRepository;
import in.ecommerce.ecommerce_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

}