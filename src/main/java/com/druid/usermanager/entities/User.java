package com.druid.usermanager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USUARIO", schema = "usermanager",catalog = "usermanagerbbdd")
@SequenceGenerator(schema = "usermanager",sequenceName = "usuario_seq",name = "generador_usuario_seq",initialValue = 5 ,allocationSize = 1)
public class User implements java.io.Serializable{

	private static final long serialVersionUID = -6078938284979656361L;
	
	private Integer id;
	private String nombre;
	private String apellidos;
	private String email;
	private String password;
	private String avatar;
	private Date fechaAlta;	
	private String rol;
	private Date fechaNacimiento;
	private Date fechaBaja;

	public User() {
	}

	public User(Integer id, String nombre, String apellidos, String email, String password, Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(generator = "generador_usuario_seq",strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NOMBRE", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "APELLIDOS", nullable = false, length = 100)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "EMAIL", nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "AVATAR", length = 1000)
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_ALTA", nullable = false, length = 26)
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Column(name = "ROL", length = 10)
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_NACIMIENTO", nullable = false, length = 10)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_BAJA", nullable = false, length = 26)
	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getApellidos() == null) {
			if (other.getApellidos() != null) {
				return false;
			}
		} else if (!getApellidos().equals(other.getApellidos())) {
			return false;
		}
		if (getEmail() == null) {
			if (other.getEmail() != null) {
				return false;
			}
		} else if (!getEmail().equals(other.getEmail())) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getNombre() == null) {
			if (other.getNombre() != null) {
				return false;
			}
		} else if (!getNombre().equals(other.getNombre())) {
			return false;
		}
		return true;
	}

}
