# Minha entrega para a Rinha de Backend Edição 2023 Q3

## Instruções e Regras para Rinha de Backend - Edição 2023 Q3

## Resumo

- As APIs precisam expor endpoints iguais e necessariamente usar um dos seguintes bancos de dados (à sua escolha): Postgres, MySQL, ou MongoDB.
- O "deploy" da API será feito via docker-compose com limites de CPU e memória.
- O teste será executado em EC2 devidamente configurado e a limitação de CPU e memória será interessante para exercitarmos ambientes com limitações, use a criatividade;
- A ferramenta Gatling será usada para rodar os testes de stress.
- A essência desse torneio não é a competição em si (até mesmo pq não ganha nada quem vencer kkk), mas compartilhar conhecimento.
- Você tem até a meia-noite do dia **22/08** aka `2023-08-22T23:59:59-03:00` para submeter seu PR. No dia 25/08 haverá uma live em https://www.youtube.com/zanfranceschi para transmitir a Rinha de Backend do vivo.


## Endpoints
As APIs precisam expor 3 (4, na verdade) endpoints:

- `POST /pessoas` – para criar um recurso pessoa.
- `GET /pessoas/[:id]` – para consultar um recurso criado com a requisição anterior.
- `GET /pessoas?t=[:termo da busca]` – para fazer uma busca por pessoas.
- `GET /contagem-pessoas` – endpoint especial para contagem de pessoas cadastradas.


### Criação de Pessoas
`POST /pessoas`

Deverá aceitar uma requisição em formato JSON com os seguintes parâmetros:

| atributo | descrição |
| --- | --- |
| **apelido** | obrigatório, único, string de até 32 caracteres. |
| **nome** | obrigatório, string de até 100 caracteres. |
| **nascimento** | obrigatório, string para data no formato AAAA-MM-DD (ano, mês, dia). |
| **stack** | opcional, vetor de string com cada elemento sendo obrigatório e de até 32 caracteres. |

Para requisições válidas, sua API deverá retornar status code 201 - created junto com o header "Location: /pessoas/[:id]" onde [:id] é o id – em formato UUID com a versão a seu critério – da pessoa que acabou de ser criada. O conteúdo do corpo fica a seu critério; retorne o que quiser. 

Exemplos de requisições válidas:
```json
{
    "apelido" : "josé",
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
}
```

```json
{
    "apelido" : "ana",
    "nome" : "Ana Barbosa",
    "nascimento" : "1985-09-23",
    "stack" : null
}
```
Para requisições inválidas, o status code deve ser 422 - Unprocessable Entity/Content. Aqui, novamente, o conteúdo do corpo fica a seu critério.

Exemplos de requisições inválidas:
```json
{
    "apelido" : "josé", // caso "josé" já tenha sido criado em outra requisição
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
}
```

```json
{
    "apelido" : "ana",
    "nome" : null, // não pode ser null
    "nascimento" : "1985-09-23",
    "stack" : null
}
```

```json
{
    "apelido" : null, // não pode ser null
    "nome" : "Ana Barbosa",
    "nascimento" : "1985-01-23",
    "stack" : null
}
```

Para o caso de requisições sintaticamente inválidas, a resposta deverá ter o status code para 400 - bad request. Exemplos:

```json
{
    "apelido" : "apelido",
    "nome" : 1, // nome deve ser string e não número
    "nascimento" : "1985-01-01",
    "stack" : null
}
```

```json
{
    "apelido" : "apelido",
    "nome" : "nome",
    "nascimento" : "1985-01-01",
    "stack" : [1, "PHP"] // stack deve ser um array de apenas strings
}
```

### Detalhe de uma Pessoa
`GET /pessoas/[:id]`

Deverá retornar os detalhes de uma pessoa caso esta tenha sido criada anteriormente. O parâmetro [:id] deve ser do tipo UUID na versão que escolher. O retorno deve ser como os exemplos a seguir.


```json
{
    "id" : "f7379ae8-8f9b-4cd5-8221-51efe19e721b",
    "apelido" : "josé",
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
}
```

```json
{
    "id" : "5ce4668c-4710-4cfb-ae5f-38988d6d49cb",
    "apelido" : "ana",
    "nome" : "Ana Barbosa",
    "nascimento" : "1985-09-23",
    "stack" : null
}
```

Note que a resposta é praticamente igual ao payload de criação com o acréscimo de `id`. O status code para pessoas que existem deve ser 200 - Ok. Para recursos que não existem, deve-se retornar 404 - Not Found.


### Busca de Pessoas
`GET /pessoas?t=[:termo da busca]`

Dado o `termo da busca`, a resposta deverá ser uma lista que satisfaça o termo informado estar contido nos atributos `apelido`, `nome`, e/ou elementos de `stack`. A busca não precisa ser paginada e poderá retornar apenas os 50 primeiros registros resultantes da filtragem para facilitar a implementação.

O status code deverá ser sempre 200 - Ok, mesmo para o caso da busca não retornar resultados (vazio).

Exemplos: Dado os recursos seguintes existentes em sua aplicação:

```json
[{
    "id" : "f7379ae8-8f9b-4cd5-8221-51efe19e721b",
    "apelido" : "josé",
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
},
{
    "id" : "5ce4668c-4710-4cfb-ae5f-38988d6d49cb",
    "apelido" : "ana",
    "nome" : "Ana Barbosa",
    "nascimento" : "1985-09-23",
    "stack" : ["Node", "Postgres"]
}]
```

Uma requisição `GET /pessoas?t=node`, deveria retornar o seguinte:
```json
[{
    "id" : "f7379ae8-8f9b-4cd5-8221-51efe19e721b",
    "apelido" : "josé",
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
},
{
    "id" : "5ce4668c-4710-4cfb-ae5f-38988d6d49cb",
    "apelido" : "ana",
    "nome" : "Ana Barbosa",
    "nascimento" : "1985-09-23",
    "stack" : ["Node", "Postgres"]
}]
```

Uma requisição `GET /pessoas?t=berto`, deveria retornar o seguinte:
```json
[{
    "id" : "f7379ae8-8f9b-4cd5-8221-51efe19e721b",
    "apelido" : "josé",
    "nome" : "José Roberto",
    "nascimento" : "2000-10-01",
    "stack" : ["C#", "Node", "Oracle"]
}]
```

Uma requisição `GET /pessoas?t=Python`, deveria retornar o seguinte:
```json
[]
```

Se a query string `t` não for informada, a resposta deve ter seu status code para 400 - bad request com o corpo que quiser. Ou seja, informar `t` é obrigatório.

### Contagem de Pessoas - Endpoint Especial
`GET /contagem-pessoas`

Este é um endpoint especial que NÃO SERÁ TESTADO (portanto, não se preocupe com sua performance) e deverá retornar em texto puro o número de registros de pessoas e qq status code na faixa de 2XX. Ele será usado para validar o número de requisições de criação bem sucedidas durante o teste de stress, por isso não use cache ou qualquer outra forma de materialização que seja eventualmente consistente.

### Nota Importante Sobre Cache e Armazenamento
Você pode usar cache, mas eventualmente todos os registros criados através das requisições `POST /pessoas` precisam ser persistidos em banco de dados em armazenamento não volátil (disco).


## Restrições de Componentes
O teste terá os seguintes componentes e topologia:

```mermaid
flowchart TD
    G(Stress Test - Gatling) -.-> LB(Load Balancer - Nginx)
    subgraph Sua Aplicação
        LB -.-> API1(API - instância 01)
        LB -.-> API2(API - instância 02)
        API1 -.-> Db[(Database)]
        API2 -.-> Db[(Database)]
    end
```

### Stress Test - Gatling
Componente que executará o teste de stress contra sua aplicação.

#### Load Balancer - Nginx
O load balancer foi incluído no teste para simular um ambiente produtivo com mais de uma instância de uma API para maior disponibilidade.

### API - instâncias 01 e 02
Como mencionado, o teste será executado com duas instâncias de sua API. Além de ficar um pouco menos distante de um ambiente produtivo, ter mais de uma instância te obriga a pensar com mais carinho sobre cache, consistência, etc. A estratégia de balanceamento para suas APIs será do tipo round-robin ou fair distribution. Ou seja, o primeiro request irá para a API 01, o segundo para a API 02, o terceiro para a API01 novamente, e assim por diante.

### Database
Como já mencionado no início do documento, você poderá optar por usar Postgres, MySQL, ou MongoDB. Fica a seu critério :)

#

Creditos: [zanfranceschi](https://github.com/zanfranceschi/rinha-de-backend-2023-q3)
