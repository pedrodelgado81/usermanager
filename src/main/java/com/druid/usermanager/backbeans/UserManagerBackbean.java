package com.druid.usermanager.backbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.druid.usermanager.entities.User;
import com.druid.usermanager.services.UserService;

@Named(value = "userManagerBackbean")
@ViewScoped
public class UserManagerBackbean implements Serializable {

	@Autowired
	private UserService userService;

	private List<User> usersList;

	private Date inicioFiltro;
	private Date finFiltro;

	@PostConstruct
	private void init() {
		usersList = this.userService.listUsers();
	}

	public boolean filterByDate(Object value, Object filter, Locale locale) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// TODO: Lanzar excepcion cuando la fecha fin es anterior a la de inicio
		try {
			if (filter != null) {
				String dates[] = ((String) filter).split("-");
				Date end = null;
				Date start = null;
				//Si tiene fecha de inicio
				if (dates[0] !=null) {
					start = formatter.parse(dates[0]);
				}
				//Si tiene fecha de fin
				if (dates.length > 1) {
					end = formatter.parse(dates[1]);
				}
				
				if(start != null && end != null) {
					return ((Date)value).getTime()>=start.getTime() && ((Date)value).getTime()<=end.getTime();
				}else if (start!=null){					
					return ((Date)value).getTime()>=start.getTime();
				}
				else if (end!=null){					
					return ((Date)value).getTime()<=end.getTime();
				}else {
					return true;
				}
				
				
			}
		} catch (java.text.ParseException ex) {
			// FIXME: Logear
			ex.printStackTrace();
			// Si no puede parsear el rango de fechas no filtra
			return true;
		}

		return true;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

	public Date getInicioFiltro() {
		return inicioFiltro;
	}

	public void setInicioFiltro(Date inicioFiltro) {
		this.inicioFiltro = inicioFiltro;
	}

	public Date getFinFiltro() {
		return finFiltro;
	}

	public void setFinFiltro(Date finFiltro) {
		this.finFiltro = finFiltro;
	}

}
