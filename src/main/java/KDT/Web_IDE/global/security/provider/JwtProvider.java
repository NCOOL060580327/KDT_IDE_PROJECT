package KDT.Web_IDE.global.security.provider;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import KDT.Web_IDE.global.exception.GlobalErrorCode;
import KDT.Web_IDE.global.exception.custom.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private final SecretKey secretKey;
  private final Long accessTokenValidityMilliseconds;
  private final Long refreshTokenValidityMilliseconds;

  public JwtProvider(
      @Value("${jwt.secret}") final String secretKey,
      @Value("${jwt.access-token-validity}") final long accessTokenValidityMilliseconds,
      @Value("${jwt.refresh-token-validity}") final long refreshTokenValidityMilliseconds) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenValidityMilliseconds = accessTokenValidityMilliseconds;
    this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
  }

  public String generateAccessToken(Long memberId) {
    return generateToken(memberId, accessTokenValidityMilliseconds);
  }

  public String generateRefreshToken(Long memberId) {
    return generateToken(memberId, refreshTokenValidityMilliseconds);
  }

  private String generateToken(Long memberId, long validityMilliseconds) {
    Claims claims = Jwts.claims();
    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime tokenValidity = now.plusSeconds(validityMilliseconds / 1000);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(memberId.toString())
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(Date.from(tokenValidity.toInstant()))
        .setIssuer("WEB_IDE")
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Long getSubject(String token) {
    return Long.valueOf(getClaims(token).getBody().getSubject());
  }

  private Jws<Claims> getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
  }

  public LocalDateTime getExpiredAt(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  public Boolean isValidToken(String token) {
    try {
      Jws<Claims> claims = getClaims(token);
      Date expiredDate = claims.getBody().getExpiration();
      return expiredDate.after(new Date());
    } catch (ExpiredJwtException e) {
      throw new TokenException(GlobalErrorCode.TOKEN_EXPIRED);
    } catch (SecurityException
        | MalformedJwtException
        | UnsupportedJwtException
        | IllegalArgumentException e) {
      throw new TokenException(GlobalErrorCode.INVALID_TOKEN);
    }
  }
}
