package com.github.PerseuAlves.jwtexample.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.PerseuAlves.jwtexample.entity.Usuario;
import com.github.PerseuAlves.jwtexample.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/*
    Classe service responsável por gerar um token válido e verificar/retornar um Subject.
    Implementa "TokenService", que é usado na classe de controle (linha TODO)
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String gerarToken(Usuario usuario) {
        return JWT
                .create()
                .withIssuer("Produtos")
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getId())
                .withExpiresAt(
                        LocalDateTime.now()
                                .plusSeconds(15)
                                .toInstant(ZoneOffset.of("-03:00"))
                )
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String getSubject(String token) {
        return JWT
                .require(Algorithm.HMAC256(secret))
                .withIssuer("Produtos")
                .build()
                .verify(token)
                .getSubject();
    }
}
