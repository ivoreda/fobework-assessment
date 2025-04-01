package com.muzan.musicbookingapp.service;


import com.muzan.musicbookingapp.dto.AuthResponse;
import com.muzan.musicbookingapp.dto.LoginRequest;
import com.muzan.musicbookingapp.dto.RegisterRequest;
import com.muzan.musicbookingapp.exception.EmailAlreadyExistsException;
import com.muzan.musicbookingapp.exception.InvalidCredentialsException;
import com.muzan.musicbookingapp.model.Artist;
import com.muzan.musicbookingapp.model.User;
import com.muzan.musicbookingapp.model.UserRole;
import com.muzan.musicbookingapp.repository.IArtistRepository;
import com.muzan.musicbookingapp.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IArtistRepository artistRepository;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        userRepository.save(user);
        if (registerRequest.getRole() == UserRole.ARTIST) {
            Artist artistProfile = new Artist();
            artistProfile.setUser(user);
            String stageName = registerRequest.getStageName() != null && !registerRequest.getStageName().isBlank()
                    ? registerRequest.getStageName()
                    : user.getFirstName() + " " + user.getLastName();
            artistProfile.setStageName(stageName);

            artistProfile.setBio(registerRequest.getBio() != null ? registerRequest.getBio() : "");

            artistRepository.save(artistProfile);
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
