package site.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configure Database Connection
 * @MapperScan(��ĵ ��ġ, SqlSessionFactory ����, ��ĳ�ʰ� �˻��� �ּ��� ����)
 */
@Configuration
@MapperScan(value = "site.mvc.mapper", sqlSessionFactoryRef= "", annotationClass = DBMapper.class)
public class DBConfig {
	
	@Bean(name = "dbDataSource")
	@Primary // �� �켱���� ����
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource dbDataSource() {
		return DataSourceBuilder
				.create()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean(name = "dbSqlSessionTemplate")
	@Primary
	public SqlSessionFactory dbSessionFactory(@Qualifier("dbDataSource") DataSource dbDataSource
			, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
    @Bean(name = "dbPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager dbPlatformTransactionManager(
            @Qualifier("dbDataSource") DataSource dbDataSource) {
        return new DataSourceTransactionManager(dbDataSource);
    }
    
}
