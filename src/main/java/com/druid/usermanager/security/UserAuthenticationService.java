package com.druid.usermanager.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.druid.usermanager.services.UserService;

@Service("autenticacionUsuariosService")
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		List<com.druid.usermanager.entities.User> usuarios = userService.findUserByEmail(username);

		if (usuarios.isEmpty() || usuarios.size() > 1) {
			throw new UsernameNotFoundException("No se ha encontrado al usuario");
		} else {

			// Crea un constructor de usuario para "usermane"
			UserBuilder userBuilder = User.withUsername(username);
			// Configura el usuario como activo
			userBuilder.disabled(false);
			// Encapsula el password (este valor es obligatorio)
			userBuilder.password(usuarios.get(0).getPassword());
			// Modo de autorizacion (este valor es obligatorio)
			if (username.contains("admin")) {
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {
				userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
			}
			// Generamos el usuario
			return userBuilder.build();
		}
	}

}
