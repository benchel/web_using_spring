package site.common.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

	@Value("${jasypt.encryptor.pswd}")
	private String encryptKey;
    
	@Bean("jasyptEncryptor")
	public SimpleStringPBEConfig jasyptEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(encryptKey);
		config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘 default
		config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수 default
		config.setPoolSize("1"); // 인스턴스 pool default
		config.setProviderName("SunJCE"); // default
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
		config.setStringOutputType("base64"); //인코딩 방식
		encryptor.setConfig(config);
		return config;
	}
}
