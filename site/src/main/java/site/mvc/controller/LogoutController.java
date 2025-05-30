package site.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	
	@GetMapping("/site/logout")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 	    
	    
		HttpSession session = request.getSession();
		session.removeAttribute("principal");
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		
		securityContextHolderStrategy.clearContext();
		SecurityContext context = securityContextHolderStrategy.getContext();
		securityContextHolderStrategy.setContext(context);
	    securityContextRepository.saveContext(context, request, response); 	    
	    
		String targetUrl = "/";
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		// 로그아웃 요청이 있었던 페이지를 기록했다면 해당 페이지의 url로 이동한다. 없는 경우 기본값으로 지정한 url로 이동
		if(savedRequest != null)
			targetUrl = savedRequest.getRedirectUrl();
		redirectStrategy.sendRedirect(request, response, targetUrl);   
	}
}
