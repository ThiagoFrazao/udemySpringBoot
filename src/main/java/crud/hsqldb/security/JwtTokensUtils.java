package crud.hsqldb.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokensUtils {
	
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_ROLE = "role";
	private static final String CLAIM_KEY_CREATED = "created";
	
	@Value("${jwt.secret}")
	private static String secret;
	
	@Value("${jwt.expiration}")
	private static Long expiration;
	
	public static String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}
	
	public static Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}
	
	public static String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
	
	public static boolean validateToken(String token) {
		return !expiredToken(token);
	}
	
	public static String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE,authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED,new Date());
		return generateToken(claims);
	}

	private static String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				   .signWith(SignatureAlgorithm.HS512,secret).compact();
	}
	
	private static Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private static boolean expiredToken(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}


	private static Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	

}
