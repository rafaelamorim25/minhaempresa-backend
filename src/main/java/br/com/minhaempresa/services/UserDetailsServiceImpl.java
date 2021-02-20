package br.com.minhaempresa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.ClienteApp;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteAppRepository;
import br.com.minhaempresa.repositories.EmpresaRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ClienteAppRepository clienteAppRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Empresa empresa = empresaRepository.findByEmail(email);
		
		if(empresa == null) {
			
			ClienteApp cliente = clienteAppRepository.findByEmail(email);
			
			if(cliente == null) {
				throw new UsernameNotFoundException(email);
			}
			
			return new User(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
		}
		
		return new User(empresa.getId(), empresa.getEmail(), empresa.getSenha(), empresa.getPerfis());
	}

}
