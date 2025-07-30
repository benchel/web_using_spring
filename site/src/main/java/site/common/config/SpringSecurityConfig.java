package site.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import site.bean.SiteAccessDeniedHandler;
import site.bean.SiteUnAuthenticationEntryPoint;
import site.mngr.bean.MngrAccessDeniedHandler;
import site.mngr.bean.MngrUnAuthenticationEntryPoint;

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
		"/", "/css/**", "/js/**", "/img/**", "/editor/uploaded/img/", "/site/login", "/site/sign/*", "/mngr/login"
	};
	
	public static final String[] SITE_AUTHENTICATED_PATTERN = {
		"/site/mypage/*", "/board/reg"
	};

	public static final String[] MNGR_AUTHENTICATED_PATTERN = {
		"/mngr/notice/*"
	};
	
	// 웹사이트 사용자의 접근과 권한 정의
	@Configuration
	@RequiredArgsConstructor
	@Order(1)
	public static class UserSecurityConfing {
		
		@Bean
		public SiteUnAuthenticationEntryPoint siteUnAuthenticationEntryPoint() {
			return new SiteUnAuthenticationEntryPoint();
		}
		
		@Bean
		public SiteAccessDeniedHandler siteAccessDeniedHandler() {
			return new SiteAccessDeniedHandler();
		}
		
		// 인증(authentication)이 필요한 접근과 인증이 불필요한 접근 설정
		// 접근 허가에 필요한 권한 설정
		@Bean("siteFilterChain")
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			http.requestMatchers((requestMatchers) -> 
				requestMatchers
					.antMatchers(SITE_AUTHENTICATED_PATTERN)
				).authorizeRequests((authorizeRequests) -> 
				authorizeRequests
					.antMatchers(SITE_AUTHENTICATED_PATTERN).authenticated()
					.antMatchers(SITE_AUTHENTICATED_PATTERN).hasAuthority("USER")
				).authorizeRequests((authorizeRequests) -> 
				authorizeRequests
					.antMatchers(SECURITY_EXCLUDE_PATTERN).permitAll()
				).exceptionHandling(exceptionHandling -> 
				exceptionHandling
					.authenticationEntryPoint(siteUnAuthenticationEntryPoint())
					.accessDeniedHandler(siteAccessDeniedHandler())
					.accessDeniedPage("/error/redirect")
				).logout((logout) ->
				logout
					.logoutUrl("/site/logout")
					.logoutRequestMatcher(new AntPathRequestMatcher("/site/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
 				);
			
			return http.build();
		}
		
	}
	
	@Configuration
	@Order(2)
	public static class MngrSecurityConfig {

		@Bean
		public MngrUnAuthenticationEntryPoint mngrUnAuthenticationEntryPoint() {
			return new MngrUnAuthenticationEntryPoint();
		}
		
		@Bean
		public MngrAccessDeniedHandler mngrAccessDeniedHandler() {
			return new MngrAccessDeniedHandler();
		}
		
		@Bean("mngrFilterChain")
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			
			http
			.requestMatchers((requestMatchers) -> requestMatchers
				.antMatchers(MNGR_AUTHENTICATED_PATTERN)
			).authorizeRequests((authorizeRequests) -> authorizeRequests
				.antMatchers(MNGR_AUTHENTICATED_PATTERN).authenticated()
				.antMatchers(MNGR_AUTHENTICATED_PATTERN).hasAuthority("MANAGER")					
			).authorizeRequests((authorizeRequests) -> authorizeRequests
				.antMatchers("/mngr/sign/in").permitAll()
			)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(mngrUnAuthenticationEntryPoint())
				.accessDeniedHandler(mngrAccessDeniedHandler())
				.accessDeniedPage("/error/redirect")
			)
			.logout((logout) ->
			logout
				.logoutUrl("/mngr/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/mngr/logout"))
				.logoutSuccessUrl("/mngr/sign/in")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			);
			
			return http.build();
		}
	    
	}
	
}
