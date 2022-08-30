package com.druid.usermanager.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Named(value = "userIdentity")
@SessionScoped
public class UserIdentity implements Serializable {

	private static final long serialVersionUID = 2882213176766533076L;

	private UserDetails authenticatedUser;

	private String selectedLocale = "es";
	
	private ResourceBundle messages;


	private List<SelectItem> locales;

	@PostConstruct
	private void init() {		
		locales = new ArrayList<SelectItem>();
		locales.add(new SelectItem("es", "Español"));
		locales.add(new SelectItem("en", "Inglés"));
		
		messages = ResourceBundle.getBundle("i18.messages",new Locale(selectedLocale,selectedLocale.toUpperCase()));
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.authenticatedUser=null;
		return "_salir";
	}
	
	public void changeLocale() {
		FacesContext.getCurrentInstance()
		.getViewRoot().setLocale(new Locale(selectedLocale,selectedLocale.toUpperCase()));
		messages = ResourceBundle.getBundle("i18.messages",new Locale(selectedLocale,selectedLocale.toUpperCase()));
	}

	public UserDetails getAuthenticatedUser() {
		if(this.authenticatedUser==null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			authenticatedUser = (UserDetails) authentication.getPrincipal();
		}
		return authenticatedUser;
	}

	public void setAuthenticatedUser(UserDetails authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String getSelectedLocale() {
		return selectedLocale;
	}

	public void setSelectedLocale(String selectedLocale) {
		this.selectedLocale = selectedLocale;
	}

	public List<SelectItem> getLocales() {
		return locales;
	}

	public void setLocales(List<SelectItem> locales) {
		this.locales = locales;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

}
