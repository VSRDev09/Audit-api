# API de Auditoria

API desenvolvida com **Spring Boot** e **Spring AI**, integrada aos modelos Gemini da Google, com o objetivo de gerar a seção **Entendimento de Controle Interno** do documento *Entendimento do Objeto*, utilizado na realização de auditorias por meio de inteligência artificial generativa.

A aplicação disponibiliza diferentes endpoints para processar entradas de usuários utilizando modelos com capacidades e níveis de processamento distintos.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring AI
- Google Gemini AI
- Maven

## Pré-requisitos

Antes de executar a aplicação, certifique-se de possuir:

- JDK instalado e configurado.
- Maven instalado (ou Maven Wrapper disponível no projeto).
- Uma API Key do Google Gemini.

## Configuração

### 1. Obtenha uma API Key

A aplicação utiliza os modelos Gemini, sendo necessário obter uma API Key através do Google AI Studio.

Configure sua chave como variável de ambiente:

**Windows PowerShell:**

```powershell
$env:GOOGLE_API_KEY="SUA_API_KEY"
```

Substitua `SUA_API_KEY` pela chave gerada.

> A variável configurada dessa forma fica disponível apenas na sessão atual do PowerShell.

### 2. Configuração da aplicação

Verifique as configurações presentes no arquivo:

```text
src/main/resources/application.properties
```

Certifique-se de que as configurações necessárias para integração com o Gemini estejam devidamente configuradas.

## Executando a aplicação

Execute a aplicação utilizando Maven:

```bash
mvn spring-boot:run
```

Ou, caso o projeto possua Maven Wrapper:

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

Após a inicialização, a API estará disponível localmente na porta configurada no projeto.

## Endpoints

A aplicação disponibiliza três endpoints para realização de auditorias:

| Método | Endpoint | Modelo |
|---|---|---|
| POST | `/api/audit/natural-text/hydrogen` | Hydrogen |
| POST | `/api/audit/natural-text/flash` | Gemini Flash |
| POST | `/api/audit/natural-text/flash-lite` | Gemini Flash Lite |

Cada endpoint recebe uma requisição de auditoria e delega seu processamento ao serviço responsável, utilizando o modelo de IA correspondente.

## Realizando uma requisição

As auditorias podem ser realizadas através de ferramentas como:

- Postman
- Insomnia
- cURL

### Exemplo de requisição

**Endpoint:**

```http
POST http://localhost:8080/api/audit/natural-text/flash
```

**Headers:**

```http
Content-Type: application/json
```

**Body (JSON):**

```json
{
  "entrada": "Esse é o link da Ata de Reunião Preliminar: https://exemplo.com/ata. O Questionário de Avaliação do Controle Interno está em https://exemplo.com/questionario."
}
```

### Exemplo utilizando cURL

```bash
curl -X POST "http://localhost:8080/api/audit/natural-text/flash" \
     -H "Content-Type: application/json" \
     -d "{\"entrada\":\"Esse é o link da Ata de Reunião Preliminar: https://exemplo.com/ata. O Questionário de Avaliação do Controle Interno está em https://exemplo.com/questionario.\"}"
```

O campo `entrada` deve conter o texto utilizado como referência para a auditoria, podendo incluir informações e links para documentos relevantes ao processo.


### Utilização do Ollama — Infraestrutura do IFBA

O IFBA disponibiliza uma API baseada no Ollama, permitindo utilizar modelos de inteligência artificial hospedados em sua infraestrutura, sem a necessidade de executá-los localmente. A API oferece acesso a modelos de linguagem (LLMs) e modelos de embeddings, possibilitando experimentações com diferentes arquiteturas e aplicações.

### Listando os modelos disponíveis

Para consultar os modelos disponíveis no servidor, utilize:

```bash
curl https://ollama-api.ifba.edu.br/api/tags | python -m json.tool
````

O comando retorna a lista de modelos disponíveis, permitindo identificar quais podem ser utilizados nas requisições.

### Realizando uma requisição de chat

É possível enviar mensagens para um modelo e obter respostas utilizando o endpoint `/api/chat`.

Exemplo utilizando o modelo `qwen3:30b`:

```bash
curl -X POST https://ollama-api.ifba.edu.br/api/chat \
     -H "Content-Type: application/json" \
     -d '{
       "model": "qwen3:30b",
       "messages": [
         {
           "role": "user",
           "content": "Olá! Explique resumidamente o que é IA."
         }
       ],
       "stream": false
     }'
```

Nesse exemplo, uma mensagem é enviada ao modelo e a resposta é retornada em uma única requisição, sem streaming, devido à configuração `"stream": false`.

### Utilizando modelos de embeddings

Além dos modelos generativos, o servidor também disponibiliza modelos de embeddings, que transformam textos em representações vetoriais. Essas representações podem ser utilizadas em aplicações que envolvem busca semântica, comparação de similaridade, recuperação de informações e arquiteturas RAG (Retrieval-Augmented Generation).

Exemplo utilizando o modelo `qwen3-embedding:4b`, disponibilizado no servidor do GSORT:

```bash
curl -s -X POST https://ollama-api.ifba.edu.br/api/embed \
     -H "Content-Type: application/json" \
     -d '{
       "model": "qwen3-embedding:4b",
       "input": "Artificial intelligence is transforming software architecture."
     }'
```

A resposta contém os vetores gerados a partir do texto informado. Existem outros parâmetros que permitem ajustar o comportamento da geração, incluindo configurações relacionadas às dimensões dos embeddings, de acordo com as capacidades do modelo utilizado.

### Experimentação

A API disponibiliza diferentes modelos e recursos que podem ser explorados para fins de estudo, desenvolvimento e experimentação. Sintam-se à vontade para testar outros modelos, parâmetros, prompts e funcionalidades disponíveis, investigando suas possibilidades e aplicações em projetos.
