package com.druid.usermanager;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ServletContextAware;

@SpringBootApplication
public class UsermanagerApplication  {

//	implements ServletContextAware
	
	public static void main(String[] args) {
		SpringApplication.run(UsermanagerApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.xhtml");
		return servletRegistrationBean;
	}
	

//	@Override
//	public void setServletContext(ServletContext servletContext) {
//		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
//	}

}
