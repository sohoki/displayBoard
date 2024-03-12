package egovframework.com.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

/**
 * @ClassName : EgovConfigAppMapper.java
 * @Description : Mapper 설정
 *
 * @author : 윤주호
 * @since  : 2021. 7. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 *   2021. 7. 20    윤주호               최초 생성
 * </pre>
 *
 */
@Configuration
@PropertySources({
	@PropertySource("classpath:/application.yml")
})
public class EgovConfigAppMapper {
	
	
	@Value("${Globals.DbType}")
	private String dbType;
	
	private String mapperConfigerLocation = "classpath:/egovframework/mapper/config/mapper-config.xml";
	
	@Primary
	@Bean
	@Lazy
	public DefaultLobHandler lobHandler() {
		return new DefaultLobHandler();
	}
	@Primary
	@Bean(name = {"sqlSession"})
	public SqlSessionFactoryBean sqlSession(@Qualifier("dataSource") DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		//mapper config 설정 
		sqlSessionFactoryBean.setConfigLocation(
			pathMatchingResourcePatternResolver
				.getResource(mapperConfigerLocation));

		try {
			
			List<String> mapperLocations = new ArrayList<>();
			mapperLocations.add("classpath:/egovframework/mapper/let/**/*_" + dbType + ".xml");
			//mapperLocations.add("classpath:/mapper/"+ dbType + "/backoffice/**/*.xml");
			
			for (String mapperLocation : mapperLocations) {
				sqlSessionFactoryBean.setMapperLocations(
						pathMatchingResourcePatternResolver
							.getResources(mapperLocation));
			}
		} catch (IOException e) {
			// TODO Exception 처리 필요
		}

		return sqlSessionFactoryBean;
	}
	@Primary
	@Bean
	public SqlSessionTemplate egovSqlSessionTemplate(@Qualifier("sqlSession") SqlSessionFactory sqlSession) {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSession);
		return sqlSessionTemplate;
	}
}
