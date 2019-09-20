package kr.co.uclick.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.MappingException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.type.Type;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//https://zgundam.tistory.com/26   -->@contfiguration 정리
//https://jaehun2841.github.io/2018/10/21/2018-10-21-java-config-import/#import   -->@importresource 정리
//스프링의 @Configuration 어노테이션은 어노테이션기반 환경구성을 돕는다. 이 어노테이션을 구현함으로써 클래스가 하나 이상의 @Bean 메소드를 제공하고 스프링 컨테이가 Bean정의를 생성하고 런타임시 그 Bean들이 요청들을 처리할 것을 선언하게 된다.
//spring framework 3.1 이후부터 cglib라이브러리나 별도의 component스캔을 위한 xml이 필요없다.
@Configuration
@ImportResource(locations = "classpath:applicationContext-ignite.xml")
@ComponentScan({ "kr.co.uclick.service" })//어노테이션이 부여된 class들 자동으로 scan하여 bean으로 등록(service,repository,controller,component 어노테이션 등)
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)// transaction management capability, similar to the support found in Spring's <tx:*> XML namespace.//aspectj로 같은 클래스 내에서 새로운 트랜잭션 만듦
//TransactionInterceptor and the proxy- or AspectJ-based advice
@EnableSpringConfigured//스프링 빈 팩토리 외부에서 인스턴스화되는 관리되지 않는 클래스 (일반적으로 주석으로 주석이 달린 클래스)에 종속성 주입을 적용하도록 현재 애플리케이션 컨텍스트에 신호를 보냅니다
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository")
//Annotation to enable JPA repositories. Will scan the package of the annotated configuration class for Spring Data repositories by default.다중 DB를 사용할 수 있다는데...
public class SpringConfiguration {

	@Bean
	@Primary//@Primary로 같은 우선순위로 있는 클래스가 여러개가 있을 시 그 중 가장 우선순위로 주입할 클래스 타입을 선택(빈객체 생성과정에서 의존관계 문제해결)
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.56.102:3306/uclick");
//		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//		dataSource.setUrl("jdbc:mariadb://104.198.127.222:3306/uclick");
		dataSource.setUsername("root");
		dataSource.setPassword("rlarldbs21");
		return dataSource;
	}
	//자체 싱글톤으로 관리됨
	
	//Spring에서 Hibernate기반의 JPA를 사용하기 위해서는 다음 Bean들이 필요합니다.
	//1.LocalContainerEntityManagerFactoryBean : SessionFactoryBean과 동일한 역활을 맡습니다. SessionFactoryBean과 동일하게 DataSource와 Hibernate Property, Entity가 위치한 package를 지정합니다. 또한 Hibernate 기반으로 동작하는 것을 지정해하는 JpaVendor를 설정해줘야지 됩니다.
	//2.HibernateJpaVendorAdapter : Hibernate vendor과 JPA간의 Adapter를 설정합니다. 간단히 showSql 정도의 Property만을 설정하면 됩니다. 매우 단순한 code입니다.(여기서는 addtionalproperties)
	//3.JpaTransactionManager : DataSource와 EntityManagerFactoryBean에서 생성되는 EntityManagerFactory를 지정하는 Bean입니다.
	
	
	@Bean
	@DependsOn("igniteSystem")//bean의 의존성 설정(생성순서 결정)
	@Primary//@primary가 여러개일 경우 NoUniqueBeanDefinitionException가 발생할 수도 있는데???
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("kr.co.uclick.entity");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
		//Hibernate에서 SessionFactoryBean과 동일한 역활을 담당하는 FactoryBean입니다. EntityManagerFactory를 생성하는 FactoryBean형태입니다.
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
		//HibernateTransactionManager : PlatformTransactionManager를 구현한 Hibernate용 TransactionManager를 등록해야지 됩니다. 
		//이는 Spring에서 @EnableTransactionManager와 같이 사용되어 @Transactional annotation을 사용할 수 있게 됩니다.
		//JPA를 지원하는 TransactionManager를 등록합니다. DataSource와 EntityManagerFactory를 등록시켜주면 됩니다.
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
		//Repository 클래스들에 대해 자동으로 예외를 Spring의 DataAccessException으로 일괄 변환해준다.
	}

	public Properties additionalProperties() {
		Properties properties = new Properties();
		//hibernate 추가설정(availablesettings사용)
		//참고 : https://docs.jboss.org/hibernate/orm/4.2/javadocs/org/hibernate/cfg/AvailableSettings.html
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");//Auto export/update schema using hbm2ddl tool.
		properties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString()); //Enable formatting of SQL logged to the console
		properties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());//Enable logging of generated SQL to the console
		properties.setProperty(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());//Names the Hibernate SQL Dialect class

		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, "1000");//Maximum JDBC batch size.

		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());//Enable the second-level cache (enabled by default)
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, Boolean.TRUE.toString());//Enable the query cache (disabled by default)
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.FALSE.toString());//Enable statistics collection
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, HibernateRegionFactory.class.getName());//The RegionFactory implementation class

		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", "cafe-grid"); 
		//Specify the name of the grid, that will be used for second level caching.
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", "NONSTRICT_READ_WRITE");

		//Set default L2 cache access type.
		//참고 : https://apacheignite-mix.readme.io/docs/hibernate-l2-cache
		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
				CustomPhysicalNamingStrategyStandardImpl.class.getName());
		//naming 전략은 CustomPhysicalNamingStrategyStandardImpl를 쓰겟음
		
		
		//밑음 hibernate l2cache-ehcache로 이용
//		properties.setProperty("hibernate.cache.use_second_level_cache", "true");//secondlevel cache enable
//		properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory"); //캐시구현제 지정
//		properties.setProperty("spring.jpa.properties.hibernate.cache.use_query_cache", "true");// query cache enable
//		properties.setProperty("spring.jpa.properties.javax.persistence.sharedCache.mode	", "ALL");
//		properties.setProperty("hibernate.cache.region_prefix", "");
		return properties;
	}
	

	
	
}
