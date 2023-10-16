package com.caio.rinha.api.services;

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

}
