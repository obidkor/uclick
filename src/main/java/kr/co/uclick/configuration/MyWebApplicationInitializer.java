package kr.co.uclick.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;


//https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html
//Here is the equivalent DispatcherServlet registration logic(web.xml), WebApplicationInitializer-style:
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletCxt) {

		
		
		// Create the 'root' Spring application context : rootcontext를 springconfiguration으로 대체
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);

		// Manage the lifecycle of the root application context serveltcontext리스너 등록(rootcontext를)
		servletCxt.addListener(new ContextLoaderListener(rootContext));


		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringWebConfiguration.class); //dispather context --> springwebconfiguration 대체

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("politech",
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		
		//endcoding filter
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false,
				"/*");
		
	}

}
