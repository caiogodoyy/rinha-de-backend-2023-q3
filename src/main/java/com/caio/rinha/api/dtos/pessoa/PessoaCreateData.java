package com.caio.rinha.api.dtos.pessoa;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PessoaCreateData(
    @NotBlank @Size(max = 32) String apelido, 
    @NotBlank @Size(max = 100) String nome, 
    @NotNull LocalDate nascimento, 
    List<@NotBlank @Size(max = 32) String> stack) {
}
