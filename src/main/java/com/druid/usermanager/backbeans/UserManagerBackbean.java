package com.druid.usermanager.backbeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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

	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	private static final String NAME_REGEX = "^[a-zA-Z\\º\\ª\\ñ\\Ñ\\- ]+$";
	private static final Pattern passwpordPattern = Pattern.compile(PASSWORD_REGEX);
	private static final Pattern namePattern = Pattern.compile(NAME_REGEX);

	private List<User> usersList;

	private Date filterBeginDate;
	private Date filferEndDate;

	// ------ Creacion de usuario -------

	private String email;
	private String password;
	private String name;
	private String surname;
	private Date birthDate;
	private String role;

	// ------ Fin creacion de usuario -------
	
	private boolean showDeleted;

	@PostConstruct
	private void init() {
		usersList = this.userService.listUsers();
	}
	
	public void reloadUsers() {
		if(showDeleted) {
			usersList = this.userService.listAllUsers();
		}else {
			usersList = this.userService.listUsers();	
		}
	}

	public void addUser() {

		if (!isNameFormat(this.name) || !isNameFormat(this.surname)) {
			FacesMessage msg = new FacesMessage("Formato Inválido",
					"El nombre o apellidos contienen caracteres no válidos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		if (this.userService.findUserByEmail(this.email) != null) {
			FacesMessage msg = new FacesMessage("Usuario existente",
					"Ya existe un usuario en el sistema con ese email");
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			User newUser = new User();
			newUser.setName(this.name);
			newUser.setSurname(this.surname);
			newUser.setEmail(this.email);
			newUser.setPassword(this.password);
			newUser.setBirthDate(this.birthDate);
			newUser.setRol(this.role);
			newUser.setCreationDate(new Date());
			this.userService.persistUser(newUser);
			FacesMessage msg = new FacesMessage("Usuario guardado", "Usuario añadido correctamente");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			this.email = null;
			this.password = null;
			this.name = null;
			this.surname = null;
			this.birthDate = null;
			this.role = null;
			// Lo añadimos directamente a la lista para evitar lanzar una consulta adicional
			// para el refresco
			// En caso de ser una aplicacion con varios usuarios concurrentes relanzariamos
			// la consulta (habria que ver cómo afecta al rendimiento si hay mucha
			// concurrencia)
			usersList.add(newUser);
		}
	}
	
	public void clearDateFilter() {
		this.filterBeginDate = null;
		this.filferEndDate=null;
	}
	
	public void removeUser(User user) {
		this.userService.removeUser(user);
		usersList.remove(user);
		FacesMessage msg = new FacesMessage("Usuario eliminado", "Usuario eliminado correctamente");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/*
	 * Validador por edad. Debe ser mayor de 14 años
	 */
	public void ageValidator(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (Period.between(toLocalDate ((Date)value), toLocalDate(new Date())).getYears() <= 14) {
			FacesMessage msg = new FacesMessage("Edad no válida",
					"La edad mínima debe ser mayor de 14 años");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	private LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void passwordValidator(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (!isPasswordValid((String) value)) {
			FacesMessage msg = new FacesMessage("Password no valido.",
					"El password no coincide o no cumple el patrón requerido");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	private boolean isNameFormat(String name) {
		Matcher matcher = namePattern.matcher(name);
		return matcher.matches();
	}

	private boolean isPasswordValid(String password) {
		Matcher matcher = passwpordPattern.matcher(password);
		return matcher.matches();
	}

	public boolean filterByDate(Object value, Object filter, Locale locale) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// TODO: Lanzar excepcion cuando la fecha fin es anterior a la de inicio
		try {
			if (filter != null) {
				String[] dates = ((String) filter).split("-");
				Date end = null;
				Date start = null;
				// Si tiene fecha de inicio
				if (dates[0] != null) {
					start = formatter.parse(dates[0]);
				}
				// Si tiene fecha de fin
				if (dates.length > 1) {
					end = formatter.parse(dates[1]);
				}

				if (start != null && end != null) {
					return ((Date) value).getTime() >= start.getTime() && ((Date) value).getTime() <= end.getTime();
				} else if (start != null) {
					return ((Date) value).getTime() >= start.getTime();
				} else if (end != null) {
					return ((Date) value).getTime() <= end.getTime();
				} else {
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



	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isShowDeleted() {
		return showDeleted;
	}

	public void setShowDeleted(boolean showDeleted) {
		this.showDeleted = showDeleted;
	}

	public Date getFilterBeginDate() {
		return filterBeginDate;
	}

	public void setFilterBeginDate(Date filterBeginDate) {
		this.filterBeginDate = filterBeginDate;
	}

	public Date getFilferEndDate() {
		return filferEndDate;
	}

	public void setFilferEndDate(Date filferEndDate) {
		this.filferEndDate = filferEndDate;
	}

}
