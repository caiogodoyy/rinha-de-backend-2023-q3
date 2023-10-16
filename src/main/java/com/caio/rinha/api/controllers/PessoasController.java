package com.caio.rinha.api.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caio.rinha.api.dtos.pessoa.PessoaCreateData;
import com.caio.rinha.api.entities.Pessoa;
import com.caio.rinha.api.services.PessoaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;
    
    @PostMapping
    @Transactional
    public ResponseEntity<?> createPessoa(@RequestBody @Valid PessoaCreateData data) {
        Pessoa pessoa = new Pessoa(data);
        pessoaService.savePessoa(pessoa);

        return ResponseEntity.created(URI.create("/pessoas/" + pessoa.getId())).body(null);
    }

}