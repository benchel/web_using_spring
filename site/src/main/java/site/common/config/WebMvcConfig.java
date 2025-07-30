package site.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	 // SpEL - Spring Expression Language
	@Value("${editor.resource.path}")
	private String EXTERNAL_RESOURCE_REAL_PATH;
	@Value("${editor.resource.url}")
	private String EXTERNAL_RESOURCE_URL;

	/**
	 * 리소스 핸들러 추가
	 * 프로젝트 외부에 존재하는 파일에 접근할 수 있도록 
	 * 리소스의 실질적인 위치와 표면적으로 드러날 경로를 매칭한다. 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(EXTERNAL_RESOURCE_URL)
			.addResourceLocations(EXTERNAL_RESOURCE_REAL_PATH);
	}
}
