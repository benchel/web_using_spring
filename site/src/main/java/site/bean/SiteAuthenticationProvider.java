package site.bean;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SiteAuthenticationProvider implements AuthenticationProvider {
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SiteAuthentication siteAuthentication = (SiteAuthentication) authentication;
		String id = (String) siteAuthentication.getPrincipal();
		String pwd = (String) siteAuthentication.getCredentials();
		
		//System.out.println("id : " + id);
		//System.out.println("pwd : " + pwd);
		
		return new SiteAuthentication(true);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return SiteAuthentication.class.equals(UsernamePasswordAuthenticationToken.class);
	}

}
