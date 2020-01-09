package crud.hsqldb.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import crud.hsqldb.security.utils.JwtTokensUtils;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{
	
	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokensUtils jwtTokensUtils; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(AUTH_HEADER);
		String username = null;
		if(token != null && token.startsWith(BEARER_PREFIX)) {
			token = token.substring(7);
			username = jwtTokensUtils.getUsernameFromToken(token);
		} 
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(jwtTokensUtils.validateToken(token)) {
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				System.out.println("token invalido...");
				return;
			}			
		}
		filterChain.doFilter(request, response);
	}
}