package com.base.boilerplate.config.jwt;

import com.base.boilerplate.auth.dto.CustomUserDetails;
import com.base.boilerplate.auth.dto.TokenDTO;
import com.base.boilerplate.auth.dto.UserDetailsDTO;
import com.base.boilerplate.auth.exception.CustomTokenException;
import com.base.boilerplate.config.GlobalSettings;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final GlobalSettings globalSettings;
    public static final String AUTHORITIES_KEY = "authorityKey";
    public static final String ROLE = "role";
    public static final String ID = "id";
    public static final String LOGIN_ID = "loginId";
    public TokenDTO generateJwtToken(UserDetailsDTO userDetailsDTO){
        Claims claims = Jwts.claims();
        claims.put(ID, userDetailsDTO.getId());
        claims.put(LOGIN_ID, userDetailsDTO.getUserDetails().getUsername());
        claims.put(ROLE, userDetailsDTO.getRoleName());

        Date accessTokenExpiresIn = new Date(System.currentTimeMillis()+globalSettings.getAccessTokenExpirationTime());
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis()+globalSettings.getRefreshTokenExpirationTime());

        String accessToken = Jwts.builder().addClaims(claims).setExpiration(accessTokenExpiresIn).signWith(SignatureAlgorithm.HS512, globalSettings.getSecretKey()).compact();
        String refreshToken = Jwts.builder().addClaims(claims).setExpiration(refreshTokenExpiresIn).signWith(SignatureAlgorithm.HS512, globalSettings.getSecretKey()).compact();

        TokenDTO tokenDTO = new TokenDTO(accessToken,refreshToken);
        return tokenDTO;

    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get(ROLE) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
//        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get(LOGIN_ID).toString());
        CustomUserDetails customUserDetails = new CustomUserDetails(claims.get(LOGIN_ID).toString(), null, claims.get(ROLE).toString());

        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }

    public boolean validateToken(String token) throws CustomTokenException {
        try {
            Jwts.parserBuilder().setSigningKey(globalSettings.getSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomTokenException(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new CustomTokenException(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new CustomTokenException(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new CustomTokenException(HttpStatus.UNAUTHORIZED, "JWT 토큰이 잘못되었습니다.");
        }
    }

    public Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(globalSettings.getSecretKey()).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
