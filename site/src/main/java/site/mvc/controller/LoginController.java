package site.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Data;
import site.bean.SiteAuthenticationManager;
import site.bean.SiteAuthenticationProvider;

/**
 * https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#store-authentication-manually
 * Storing the Authentication manually
 */
@Controller
public class LoginController {

	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository(); 
	
	@PostMapping("/site/login")
	public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) { 
	    UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
	        loginRequest.getId(), loginRequest.getPwd());
	    
	    SiteAuthenticationProvider siteAuthenticationProvider = new SiteAuthenticationProvider();
	    SiteAuthenticationManager siteAuthenticationManager = new SiteAuthenticationManager(siteAuthenticationProvider);
	    
	    Authentication authentication = siteAuthenticationManager.authenticate(token); 
	    SecurityContext context = SecurityContextHolder.createEmptyContext();
	    context.setAuthentication(authentication); 
	    SecurityContextHolder.setContext(context);
	    securityContextRepository.saveContext(context, request, response); 
	}
	
}

@Data
class LoginRequest {
    private String id;
    private String pwd;
}
