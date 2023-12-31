package com.caio.rinha.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caio.rinha.api.dtos.pessoa.PessoaCreateData;
import com.caio.rinha.api.dtos.pessoa.PessoaData;
import com.caio.rinha.api.entities.Pessoa;
import com.caio.rinha.api.services.PessoaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;
    
    @Transactional
    @PostMapping("/pessoas")
    public ResponseEntity<?> createPessoa(@RequestBody @Valid PessoaCreateData data) {
        Pessoa pessoa = new Pessoa(data);
        pessoaService.savePessoa(pessoa);

        return ResponseEntity.created(URI.create("/pessoas/" + pessoa.getId())).build();
    }

    @GetMapping("pessoas/{id}")
    public ResponseEntity<?> detailPessoa(@PathVariable UUID id) {
        Pessoa pessoa = pessoaService.getPessoaById(id);

        PessoaData body = new PessoaData(pessoa);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<?> searchPessoas(@RequestParam String t) {
        if (t.isEmpty())
            return ResponseEntity.badRequest().body("The search term cannot be empty...\nPlease provide a valid value for the 't' parameter.");

        Optional<List<Pessoa>> pessoas = pessoaService.getPessoasByTerm(t);

        if (pessoas.get().isEmpty())
            return ResponseEntity.ok(pessoas);

        List<PessoaData> body = pessoas.get().stream().map(PessoaData::new).toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<?> countPessoas() {
        return ResponseEntity.ok(pessoaService.countPessoas());
    }

}
