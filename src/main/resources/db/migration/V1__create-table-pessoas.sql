CREATE TABLE pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(32) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    nascimento DATE NOT NULL,
    stack VARCHAR,
    search_term TEXT GENERATED ALWAYS AS (LOWER(apelido || nome || COALESCE(stack, ''))) STORED
);

CREATE INDEX idx_search_term ON pessoas (search_term);
