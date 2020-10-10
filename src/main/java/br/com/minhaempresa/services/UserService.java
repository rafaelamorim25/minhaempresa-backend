package br.com.minhaempresa.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.minhaempresa.login.User;

public class UserService {
	
	public static User authenticated() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

}
