package com.bagus.product_management.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bagus.product_management.controller.AuthenticateRequest;
import com.bagus.product_management.controller.AuthenticationResponse;
import com.bagus.product_management.controller.RegisterRequest;
import com.bagus.product_management.security.user.User;
import com.bagus.product_management.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
      .username(request.getUsername())
      .password(passwordEncoder.encode(request.getPassword()))
      .build();

    userRepository.save(user);

    return AuthenticationResponse.builder()
      .token(jwtService.generateToken(user))
      .build();
  }

  public AuthenticationResponse authenticate(AuthenticateRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );

    var user = userRepository.findByUsername(request.getUsername())
      .orElseThrow();

    return AuthenticationResponse.builder()
      .token(jwtService.generateToken(user))
      .build();
  }
}
