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

		String name = (String) authentication.getName();
		String id = (String) authentication.getPrincipal();
		String pwd = (String) authentication.getCredentials();
		
		System.out.println("name : " + name);
		System.out.println("id : " + id);
		System.out.println("pwd : " + pwd);
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
