package com.github.PerseuAlves.jwtexample.config;

import com.github.PerseuAlves.jwtexample.entity.Usuario;
import com.github.PerseuAlves.jwtexample.repository.UsuarioRepository;
import com.github.PerseuAlves.jwtexample.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
    Classe responsável por realizar o filtro para cada requisição Rest.
    Irá verificar se o Token enviado é/está válido
 */
@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token;
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ", "");

            String subject = tokenService.getSubject(token);
            Usuario usuario = usuarioRepository.findByUsername(subject);

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
