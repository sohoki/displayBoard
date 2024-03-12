package egovframework.com.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName : EgovConfigAppDatasource.java
 * @Description : DataSource 설정
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
public class EgovConfigAppDatasource implements ApplicationListener<ApplicationStartedEvent>{

	/**
	 *  @Value 을 어노테이션을 이용하는 방법
	 */
	@Value("${Globals.DbType}")
	private String dbType;
	
	@Value("${Globals.mysql.DriverClassName}")
	private String className;	
	
	@Value("${Globals.mysql.Url}")
	private String url;
	
	@Value("${Globals.mysql.UserName}")
	private String userName;
	
	@Value("${Globals.mysql.Password}")
	private String password;

	/**
	 *  Environment 의존성 주입하여 사용하는 방법
	 */

	/*
	@PostConstruct
	void init() {
		
		
		
		dbType = dbType;
		System.out.println("##### dbType #####" + dbType);
		//Exception 처리 필요
		className = env.getProperty("Globals." + dbType + ".DriverClassName");
		url = env.getProperty("Globals." + dbType + ".Url");
		userName = env.getProperty("Globals." + dbType + ".UserName");
		password = env.getProperty("Globals." + dbType + ".Password");
	}
	*/

	/**
	 * @return [dataSource 설정] basicDataSource 설정
	 */
	private DataSource basicDataSource() {

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(className);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		basicDataSource.setInitialSize(3); //최초 로드될 때 생성할 connection 개수
		basicDataSource.setMaxTotal(10); //사용할 최대 connection 개수 
		basicDataSource.setMaxWaitMillis(30000);
		basicDataSource.setMaxIdle(10);
		basicDataSource.setMinIdle(2);
		basicDataSource.setValidationQuery("select 1");
		basicDataSource.setTestOnBorrow(false);
		basicDataSource.setTestOnReturn(false);
        // Connetion pool 검사 testWhileIdle 설정. 'TimeBetweenEvictionRunsMillis'마다 'NumTestsPerEvictionRun'개의 connection 꺼내 'ValidationQuery'를 날려 확인
		basicDataSource.setTimeBetweenEvictionRunsMillis(5000);
		return basicDataSource;
	}

	/**
	 * @return [DataSource 설정]
	 */
	@Primary
	@Bean(name = {"dataSource", "egov.dataSource", "egovDataSource"})
	public DataSource dataSource() {
		return basicDataSource();
		
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		// TODO Auto-generated method stub
		
	}
}
