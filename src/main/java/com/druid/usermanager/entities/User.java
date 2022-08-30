package com.druid.usermanager.entities;

import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USERS", schema = "usermanager",catalog = "usermanagerbbdd")
@SequenceGenerator(schema = "usermanager",sequenceName = "user_seq",name = "user_seq_generator",initialValue = 5 ,allocationSize = 1)
public class User implements java.io.Serializable{

	private static final long serialVersionUID = -6078938284979656361L;
	
	private Integer id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Date creationDate;	
	private String role;
	private Date birthDate;
	private Date deletionDate;

	public User() {
	}

	public User(Integer id, String name, String surname, String email, String password, Date birthDate) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(generator = "user_seq_generator",strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SURNAME", nullable = false, length = 100)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "CREATION_DATE", nullable = false, length = 26)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "ROLE", length = 10)
	public String getRol() {
		return role;
	}

	public void setRol(String role) {
		this.role = role;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "BIRTH_DATE", nullable = false, length = 10)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DELETION_DATE", nullable = true, length = 26)
	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
	@Transient
	public String getFullName() { 
		return this.name + " "+this.surname;
	}
	
	@Transient
	public String getFormatedDate(Date date) {
		if(date == null) {
			return "--";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
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
		if (getSurname() == null) {
			if (other.getSurname() != null) {
				return false;
			}
		} else if (!getSurname().equals(other.getSurname())) {
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
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

}
