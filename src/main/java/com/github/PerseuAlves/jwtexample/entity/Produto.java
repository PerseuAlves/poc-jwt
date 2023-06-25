package com.github.PerseuAlves.jwtexample.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "produtos")
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "preco")
    private Double preco;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "usuario_id")
    private Long usuarioId;
}
