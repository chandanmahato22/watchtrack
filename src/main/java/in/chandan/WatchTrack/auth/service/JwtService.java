package in.chandan.WatchTrack.auth.service;

import in.chandan.WatchTrack.auth.dto.LoginDto;
import in.chandan.WatchTrack.auth.entity.PrincipalUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private long expiry;

    @Value("${jwt.secret}")
    private String secret;

    public String getToken(LoginDto req) {
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .issuer(issuer)
                .subject(req.getUsernameOrEmail())
                .expiration(new Date(System.currentTimeMillis() + expiry))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, PrincipalUser details){
        String username = getUsername(token);
        return ((details.getUsername().equals(username) || (details.getEmail().equals(username))) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        Date time = Jwts.
                parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return time.before(new Date());
    }

    public SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
