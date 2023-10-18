package com.caio.rinha.api.dtos.pessoa;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PessoaCreateData(
    @Size(max = 32) String apelido, 
    @Pattern(regexp = "\\D+") @Size(min = 1, max = 100) String nome, 
    LocalDate nascimento, 
    List<@NotBlank @Pattern(regexp = "\\D+") @Size(max = 32) String> stack) {
}
