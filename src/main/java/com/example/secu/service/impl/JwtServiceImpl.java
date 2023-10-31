package com.example.secu.service.impl;

import com.example.secu.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  private SecretKey signKey;

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return Jwts
      .builder()
      .subject(userDetails.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
      .signWith(getSignKey(), Jwts.SIG.HS256)
      .compact();
  }

  @Override
  public String generateRefreshToken(
    Map<String, Object> extraClaims,
    UserDetails userDetails
  ) {
    return Jwts
      .builder()
      .claims(extraClaims)
      .subject(userDetails.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(
        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)
      )
      .signWith(getSignKey())
      .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimResolvers.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(getSignKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private SecretKey getSignKey() {
    if (signKey == null) {
      signKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    return signKey;
  }
}
