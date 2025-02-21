package net.vleven.ems_backend.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.vleven.ems_backend.model.Role;




@Component
public class JwtUtil {

	private static final String SECRET_KEY = "CMf24M5TnA/MCTBGESLZgKzAM0EShfag2QFx9dub/p0=";
	
	private long expirationMs = 3600000; // 1 hour

    public String generateToken(String username, Set<Role> roles) {
        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles.stream().map(Enum::name).collect(Collectors.toList())) // Store roles properly
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }


	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	public List<String> extractRoles(String token) {
	    Claims claims = extractAllClaims(token); //  Correct method call
	    return claims.get("roles", List.class);
	}


	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
}
