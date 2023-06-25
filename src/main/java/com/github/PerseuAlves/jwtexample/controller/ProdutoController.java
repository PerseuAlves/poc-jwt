package com.github.PerseuAlves.jwtexample.controller;

import com.github.PerseuAlves.jwtexample.entity.Produto;
import com.github.PerseuAlves.jwtexample.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    @Secured("ADMIN")
    public ResponseEntity<List<Produto>> getProdutos() {
        return ResponseEntity.ok(produtoService.getProdutos());
    }
}
