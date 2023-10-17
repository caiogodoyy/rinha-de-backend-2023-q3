package com.caio.rinha.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.rinha.api.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("""
        SELECT p FROM Pessoa p
        WHERE p.apelido LIKE :word OR
        p.nome LIKE :word OR
        EXISTS (SELECT 1 FROM UNNEST(p.stack) AS s WHERE s LIKE :word)
    """)
    public Optional<List<Pessoa>> findByWord(String word);

}
