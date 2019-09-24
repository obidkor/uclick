package kr.co.uclick.configuration;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//Spring의 mvc를 간단하게 설정할 수 있는 방법
@Configuration
@EnableWebMvc
@ComponentScan("kr.co.uclick.controller")
public class SpringWebConfiguration implements WebMvcConfigurer {
//Defines callback methods for Spring MVC
	
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		//https://zgundam.tistory.com/16
		configurer.favorPathExtension(false); // httprequset header에서 해당타입 리턴타입 결정
		configurer.favorParameter(true); // url호출시 특정 파라미터로 리턴포맷전달 허용
		configurer.parameterName("mediaType"); //HTTP 사양에 정의 매개 변수에 대한 지원을 추가
		configurer.ignoreAcceptHeader(true);
		configurer.useJaf(false);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		//ignoreAcceptHeader (true)를하면 JSON을 반환하는 XML API도 모두 JSON
		//ignoreAcceptHeader (false)가 기본값 인 경우 XML
	}
	
	//특정url진입시 로그인 검사 / 토큰검사 / 계정권한에 다라 접근막아야할때
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
		//addInterceptor는 등록할 인터셉터를 설정하며, addPathPatterns는 적용할 url 패턴을 설정합니다.
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		//디폴트 서블릿 핸들러를 위한 설정 담당
		configurer.enable();
	}

	@Bean
	public LocaleResolver LocaleResolver() {
		//스프링 MVC는 LocaleResolver를 이용해서 웹 요청과 관련된 Locale을 추출하고, 이 Locale 객체를 이용해서 알맞은 언어의 메시지를 선택
		return new CookieLocaleResolver();//쿠키를 이용해서 Locale 정보를 구한다.
	}

	@Bean
	public MessageSource messageSource() {
		// Spring에서는 MessageSource를 이용해 properties 파일로부터 에러메시지를 가져오도록 할 수 있다. 
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("validate-message.properties");
		return resourceBundleMessageSource;
		//ResourceBundleMessageSource: 리소스 파일로부터 메시지를 읽어와 등록한다.
	}

	//view resolver 설정(뷰이름--> 뷰객체 결정)
	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		//Default(기본) 뷰 리졸버. JSP를 뷰로 사용할 때
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	
	//만약 js,css등 리소스 폴더를 못찾을 경우
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//          .addResourceHandler("/resources/**")
//          .addResourceLocations("/resources/").setCachePeriod(31556926); 
//    }
}
