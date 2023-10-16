package com.caio.rinha.api.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.caio.rinha.api.dtos.pessoa.PessoaCreateData;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoas")
@Entity
public class Pessoa {
    
    @Id
    private UUID id;
    private String apelido;
    private String nome;
    private LocalDate nascimento;
    private List<String> stack;

    public Pessoa(PessoaCreateData data) {
        this.id = UUID.randomUUID();
        this.apelido = data.apelido();
        this.nome = data.nome();
        this.nascimento = data.nascimento();
        this.stack = data.stack();
    }
    
}
