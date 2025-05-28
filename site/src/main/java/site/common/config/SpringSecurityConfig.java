package site.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;
import site.bean.SiteAuthenticationManager;
import site.bean.SiteAuthenticationProvider;

/**
 * 스프링 시큐리티 설정
 * https://docs.spring.io/spring-security/reference/servlet/getting-started.html
 * https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html
 * https://www.youtube.com/watch?v=Mw_1h9K0O-w
 */
@EnableWebSecurity
public class SpringSecurityConfig {

	// Spring Security에서 제외할 웹 리소스 패스
	public static final String[] SECURITY_EXCLUDE_PATTERN = {
		"/", "/css/**", "/js/**", "/img/**", "/site/login", "/site/sign/*"
	};
	
	public static final String[] SITE_AUTHENTICATED_PATTERN = {
		"/site/mypage/**", "/board/reg"
	};
	
	// 웹사이트 사용자의 접근과 권한 정의
	@Configuration
	@AllArgsConstructor
	@Order(1)
	public static class UserSecurityConfing {

		private final SiteAuthenticationManager siteAuthenticationManager;
		private final SiteAuthenticationProvider siteAuthenticationProvider;
		
		// 인증(authentication)이 필요한 접근과 인증이 불필요한 접근 설정
		// 접근 허가에 필요한 권한 설정
		@Bean("siteFilterChain")
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			http.authenticationProvider(siteAuthenticationProvider)
				.authenticationManager(siteAuthenticationManager)
				.requestMatchers()
					.antMatchers(SITE_AUTHENTICATED_PATTERN)
					.and()
				.authorizeRequests()
					.antMatchers(SITE_AUTHENTICATED_PATTERN).authenticated()
					.antMatchers(SITE_AUTHENTICATED_PATTERN).hasAnyAuthority("USER")
					.and()
				.authorizeRequests()
					.antMatchers(SECURITY_EXCLUDE_PATTERN).permitAll()
					.and()
				.formLogin()
					.permitAll()
					.loginPage("/site/sign/in")
					.usernameParameter("id")
					.passwordParameter("pwd")
					.loginProcessingUrl("/site/login")
					//.successHandler(null) // 로그인 성공 이후의 동작 핸들링 
					//.failureHandler(null) // 로그인 실패 이후의 동작 핸들링 
					.and()
				.logout()
					.permitAll()
					.logoutRequestMatcher(new AntPathRequestMatcher("/site/logout"))
					.logoutSuccessUrl("/site/logout")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID");
			
			return http.build();
		}
		
	}
	
	// 웹사이트 관리자의 접근과 권한 정의
	@Configuration
	public static class MngrSecurityConfig {
		
		// 인증(authentication)이 필요한 접근과 인증이 불필요한 접근 설정
		// 접근 허가에 필요한 권한 설정
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return null;
		}
	}
	
}
