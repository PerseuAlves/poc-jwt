package com.github.PerseuAlves.jwtexample.repository;

import com.github.PerseuAlves.jwtexample.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
}
