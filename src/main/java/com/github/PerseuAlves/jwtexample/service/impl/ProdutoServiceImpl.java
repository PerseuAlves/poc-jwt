package com.github.PerseuAlves.jwtexample.service.impl;

import com.github.PerseuAlves.jwtexample.entity.Produto;
import com.github.PerseuAlves.jwtexample.repository.ProdutoRepository;
import com.github.PerseuAlves.jwtexample.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public List<Produto> getProdutos() {
        return produtoRepository.findAll();
    }
}
