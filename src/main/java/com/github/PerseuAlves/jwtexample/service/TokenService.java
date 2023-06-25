package com.github.PerseuAlves.jwtexample.service;

import com.github.PerseuAlves.jwtexample.entity.Usuario;

public interface TokenService {

    String gerarToken(Usuario usuario);

    String getSubject(String token);
}
