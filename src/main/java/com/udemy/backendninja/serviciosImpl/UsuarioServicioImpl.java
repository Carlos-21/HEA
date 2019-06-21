package com.udemy.backendninja.serviciosImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.model.UserRole;
import com.udemy.backendninja.repository.RepositoryUsuario;

@Service("userService")
public class UsuarioServicioImpl implements UserDetailsService {
	@Autowired
	@Qualifier("userRepository")
	private RepositoryUsuario usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.udemy.backendninja.model.User user = usuarioRepository.findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		System.out.println("======================================"+user.getUserRole());
		return buildUser(user, authorities);
	}

	private User buildUser(com.udemy.backendninja.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getContrasena(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles) {
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<GrantedAuthority>();
	}
}
