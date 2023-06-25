package com.github.PerseuAlves.jwtexample.controller;

import com.github.PerseuAlves.jwtexample.dto.Login;
import com.github.PerseuAlves.jwtexample.entity.Usuario;
import com.github.PerseuAlves.jwtexample.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
    Classe de controle responsável pelo Login do usuário
 */
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    @PostMapping("/login")
    public String login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        login.username(),
                        login.password()
                ); // Entidade a ser autenticada

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken); // Realiza a autenticação do usuário
        Usuario usuario = (Usuario) authenticate.getPrincipal(); // Retorna o usuário autenticado

        return tokenServiceImpl.gerarToken(usuario); // retorna um token gerado com base no usuário autenticado
    }
}
