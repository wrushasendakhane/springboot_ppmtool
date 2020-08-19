package com.learnspringboot.ppmtool.security;

import static com.learnspringboot.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.learnspringboot.ppmtool.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.learnspringboot.ppmtool.domain.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
  public String generateToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    Date now = new Date(System.currentTimeMillis());

    Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

    String userId = Long.toString(user.getId());

    Map<String, Object> claims = new HashMap<>();

    claims.put("id", userId);
    claims.put("username", user.getUsername());
    claims.put("fullName", user.getFullName());

    return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, SECRET).compact();

  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      System.out.println("Invalid JWT Signature");
    } catch (MalformedJwtException ex) {
      System.out.println("Invalid JWT Token");
    } catch (ExpiredJwtException ex) {
      System.out.println("Expired JWT Token");
    } catch (UnsupportedJwtException ex) {
      System.out.println("Unsupported JWT Token");
    } catch (IllegalArgumentException ex) {
      System.out.println("JWT claims string is empty");
    }

    return false;
  }

  public Long getUserIdFromJwt(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    String id = (String) claims.get("id");
    return Long.parseLong(id);
  }
}