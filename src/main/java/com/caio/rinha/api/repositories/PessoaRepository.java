package com.caio.rinha.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.rinha.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("""
        select p from Pessoa p where 
        p.apelido = :word or 
        p.nome = :word or 
        :word = any(p.stack)
    """)
    public Optional<List<Pessoa>> findByWord(String word);

}
