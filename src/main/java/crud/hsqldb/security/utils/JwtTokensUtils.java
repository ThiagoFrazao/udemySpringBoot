package crud.hsqldb.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokensUtils {
	
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_ROLE = "role";
	private static final String CLAIM_KEY_CREATED = "created";
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}
	
	public  Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}
	
	public  String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
	
	public  boolean validateToken(String token) {
		return !expiredToken(token);
	}
	
	public  String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE,authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED,new Date());
		return generateToken(claims);
	}

	private  String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				   .signWith(SignatureAlgorithm.HS512,secret).compact();
	}
	
	private  Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private  boolean expiredToken(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	private  Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}
