package site.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import site.bean.SiteAuthentication;
import site.bean.SiteAuthenticationManager;

@Component
@AllArgsConstructor
public class SiteAuthenticationFilter extends OncePerRequestFilter {

	private final SiteAuthenticationManager siteAuthManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// GET 방식으로 가져오는 데이터
		SiteAuthentication siteAuth = new SiteAuthentication(false);
		Authentication authObj = siteAuthManager.authenticate(siteAuth);
		SecurityContextHolder.getContext().setAuthentication(authObj);
		
		filterChain.doFilter(request, response);
	}
}
