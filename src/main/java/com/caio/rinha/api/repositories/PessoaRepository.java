package com.caio.rinha.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.rinha.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query(value = """
        SELECT * FROM pessoas p 
        WHERE p.search_term LIKE CONCAT('%', :term, '%')
        LIMIT 50
    """, nativeQuery = true)
    public Optional<List<Pessoa>> findByTerm(String term);

}
