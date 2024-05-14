package com.finalproject.azercell.configuration.security;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.enums.RoleEnum;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtUtil {
    //    private final UserRepository repository;
    private final NumberRepository repository;
    //    @Value("${jwt_k.key}")
    private final String secret_key = "QmFzZTY0IGVuY29kaW5nIHNjaGVtZXMgYXJlIGNvbW1vbmx5IHVzZWQgd2hlbiB0aGVyZSBpcyBhIG5lZWQgdG8gZW5jb2RlIGJpbmFyeSBkYXRhLCBlc3BlY2lhbGx5IHdoZW4gdGhhdCBkYXRhIG5lZWRzIHRvIGJlIHN0b3JlZCBhbmQgdHJhbnNmZXJyZWQgb3ZlciBtZWRpYSB0aGF0IGFyZSBkZXNpZ25lZCB0byBkZWFsIHdpdGggdGV4dC4gVGhpcyBlbmNvZGluZyBoZWxwcyB0byBlbnN1cmUgdGhhdCB0aGUgZGF0YSByZW1haW5zIGludGFjdCB3aXRob3V0IG1vZGlmaWNhdGlvbiBkdXJpbmcgdHJhbnNwb3J0LiBCYXNlNjQgaXMgdXNlZCBjb21tb25seSBpbiBhIG51bWJlciBvZiBhcHBsaWNhdGlvbnMgaW5jbHVkaW5nIGVtYWlsIHZpYSBNSU1FLCBhcyB3ZWxsIGFzIHN0b3JpbmcgY29tcGxleCBkYXRh";
    //    @Value("${app.security.jwt.expiration}")
    private final long accessTokenValidity = 86400000;
    private static Key key;


    public Key initializeKey(){
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret_key);
        key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
    public String createToken(NumberEntity client) throws Throwable {
        key = initializeKey();
        client = repository.findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("CLIENT_NOT_FOUND"));
        Claims claims = Jwts.claims().setSubject(String.valueOf(client.getNumber()));

        Set<RoleEnum> authorities = Set.of(client.getUser().getRole());
        List<String> roles = new ArrayList<>();
        for (RoleEnum authority : authorities) {
            roles.add(authority.name());
        }
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities",roles);
//        claimsMap.put("username", client.getUsername());
        claimsMap.put("user_id", client.getUser().getId());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(client.getNumber())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS512);
        log.info("Jwt token created for user: {}", client.getId());
        return jwtBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }



    public Integer getUserId(Claims claims){
        return (Integer) claims.get("user_id");
    }



    public Collection<GrantedAuthority> extractAuthorities(Claims claims) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (claims.containsKey("authorities")) {
            List<String> roles = (List<String>) claims.get("authorities");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        return authorities;
    }

}