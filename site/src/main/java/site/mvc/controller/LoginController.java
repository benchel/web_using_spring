package site.mvc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import site.bean.SiteAuthenticationManager;
import site.bean.SiteAuthenticationProvider;
import site.mvc.service.UserDetailService;

/**
 * https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#store-authentication-manually
 * Storing the Authentication manually
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

	
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	private final UserDetailService userDetailService;
	
	@PostMapping("/site/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 
	    UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
	        loginRequest.getId(), loginRequest.getPwd());
	    
	    Map<String, Object> rs = new HashMap<>();
	    
	    SiteAuthenticationProvider siteAuthenticationProvider = new SiteAuthenticationProvider(userDetailService);
	    SiteAuthenticationManager siteAuthenticationManager = new SiteAuthenticationManager(siteAuthenticationProvider);
	    
	    Authentication authentication = siteAuthenticationManager.authenticate(token);
	    HttpSession session = request.getSession();
	    session.setAttribute("principal", authentication.getPrincipal());
	    
	    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
	    context.setAuthentication(authentication);
	    securityContextHolderStrategy.setContext(context);
	    securityContextRepository.saveContext(context, request, response);
	    
	    if(authentication.isAuthenticated()) {
		    rs.put("link", onAuthenticationSuccess(request, response, authentication));
	    } else {
	    	rs.put("link", "");
	    }
	    
	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	static String onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String targetUrl = "/";
		
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		// 로그인 요청이 있었던 페이지를 기록했다면 해당 페이지의 url로 이동한다. 
		// 없다면 기본값으로 지정한 url로 이동한다.
		if(savedRequest != null) 
			targetUrl = savedRequest.getRedirectUrl();
		return targetUrl;
	}
	
	static void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	}
	
}

@Data
class LoginRequest {
    private String id;
    private String pwd;
}

