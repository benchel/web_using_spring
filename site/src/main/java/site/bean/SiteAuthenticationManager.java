package site.bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SiteAuthenticationManager implements AuthenticationManager {

	private final SiteAuthenticationProvider siteAuthProvider;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return siteAuthProvider.authenticate(authentication);
	}
}
