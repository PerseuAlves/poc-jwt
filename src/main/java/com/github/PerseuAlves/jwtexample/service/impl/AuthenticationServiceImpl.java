package com.github.PerseuAlves.jwtexample.service.impl;

import com.github.PerseuAlves.jwtexample.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    Classe service responsável por buscar um usuário válido no banco. Implementa "UserDetailsService", que é usado
    na classe de controle (linha TODO)
 */
@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }
}
