package com.caio.rinha.api.dtos.pessoa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.caio.rinha.api.entities.Pessoa;

public record PessoaData(UUID id, String apelido, String nome, LocalDate nascimento, List<String> stack) {
    public PessoaData(Pessoa pessoa) {
        this(pessoa.getId(), pessoa.getApelido(), pessoa.getNome(), pessoa.getNascimento(), Arrays.asList(pessoa.getStack().split("\\|")));
    }
}
