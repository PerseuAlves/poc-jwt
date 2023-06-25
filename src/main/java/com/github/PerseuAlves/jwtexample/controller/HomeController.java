package com.github.PerseuAlves.jwtexample.controller;

import com.github.PerseuAlves.jwtexample.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> getHome() {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = usuario.getAuthorities().stream().toList().get(0).getAuthority();

        return role.equals("ADMIN") ?
                ResponseEntity.ok("Home Page - Admin") :
                ResponseEntity.ok("Home Page - User");
    }
}
