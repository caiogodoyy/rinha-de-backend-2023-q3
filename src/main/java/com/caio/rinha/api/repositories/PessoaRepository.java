package com.caio.rinha.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.rinha.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("""
        select p from Pessoa p 
        where lower(p.apelido) like lower(:term) 
        or lower(p.nome) like lower(concat('%', :term, '%'))
        or lower(p.stack) like lower(concat('%', :term, '%'))
    """)
    public Optional<List<Pessoa>> findByTerm(String term);    

}
