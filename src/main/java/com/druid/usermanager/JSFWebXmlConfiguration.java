package com.druid.usermanager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JSFWebXmlConfiguration implements ServletContextInitializer {
	//Spring ignora muchos valores de configuracion del fichero web.xml esta clase se ha creado para poder configurar dichos valores 
	 @Override
	    public void onStartup(ServletContext sc) throws ServletException {
	        sc.setInitParameter("primefaces.THEME", "nova-light");
	        sc.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
	    }

}

