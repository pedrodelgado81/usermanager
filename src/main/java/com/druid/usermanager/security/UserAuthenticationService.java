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
		com.druid.usermanager.entities.User user = userService.findUserByEmail(username);

		if (user==null) {
			throw new UsernameNotFoundException("No se ha encontrado al usuario");
		} else {

			// Crea un constructor de usuario para "usermane"
			UserBuilder userBuilder = User.withUsername(username);			
			// Configura el usuario como activo
			userBuilder.disabled(false);
			// Encapsula el password (este valor es obligatorio)
			userBuilder.password(user.getPassword());
			// Modo de autorizacion (este valor es obligatorio)
			
			userBuilder.authorities(new SimpleGrantedAuthority(user.getRol()));
			
			// Generamos el usuario
			return userBuilder.build();
		}
	}

}
