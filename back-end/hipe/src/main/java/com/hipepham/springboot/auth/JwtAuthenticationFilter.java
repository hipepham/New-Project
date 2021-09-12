package com.hipepham.springboot.auth;

import com.hipepham.springboot.auth.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			// Lấy jwt từ request
			String jwt = getJwtFromRequest(request);

			if (org.springframework.util.StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

				// Lấy id user từ chuỗi jwt
				String username = tokenProvider.getUserNameFromJWT(jwt);

				// Lấy thông tin người dùng từ id
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				if (userDetails != null) {

					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String requestURI = request.getRequestURI();
		boolean check = requestURI.endsWith("v1/authorization/login");
		return requestURI.endsWith("v1/authorization/login");
	}
}
