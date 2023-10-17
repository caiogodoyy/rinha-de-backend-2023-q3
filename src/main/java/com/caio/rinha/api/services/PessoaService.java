package com.caio.rinha.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caio.rinha.api.entities.Pessoa;
import com.caio.rinha.api.repositories.PessoaRepository;

@Service
public class PessoaService {
 
    @Autowired
    private PessoaRepository pessoaRepository;

    public void savePessoa(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    public Pessoa getPessoaById(UUID id) {
        return pessoaRepository.getReferenceById(id);
    }

    public Optional<List<Pessoa>> getPessoasByWord(String t) {
        return pessoaRepository.findByWord(t);
    }

    public long countPessoas() {
        return pessoaRepository.count();
    }

}
