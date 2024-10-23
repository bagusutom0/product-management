package com.bagus.product_management.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    UserDetails userDetails
  ) {
    return Jwts
      .builder()
      .claims(extraClaims)
      .subject(userDetails.getUsername())
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)) // 1 hour
      .signWith(getSigningKey())
      .compact();
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String extractUsername(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
    return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
  }
}
