# Auditoria AI API

API REST desenvolvida com **Spring Boot e Spring AI** para gerar a seção **Entendimento do Controle Interno** de documentos de auditoria, utilizando modelos da família Gemini.

A aplicação recebe informações sobre os controles internos e reuniões realizadas, processa esses dados com inteligência artificial e retorna o conteúdo estruturado em blocos de texto e hyperlinks, pronto para renderização no frontend.

## Tecnologias

- Java 21
- Spring Boot
- Spring AI
- Google Gemini
- Gradle

## Endpoints

A API disponibiliza três endpoints, cada um utilizando um modelo diferente do Gemini.

| Método | Endpoint | Modelo |
|---|---|---|
| POST | `/api/audit/hydrogen` | Gemini Hydrogen |
| POST | `/api/audit/flash` | Gemini Flash |
| POST | `/api/audit/flash-lite` | Gemini Flash Lite |

**URL base local:** `http://localhost:8080`

Todos os endpoints recebem o mesmo formato de requisição.

## Requisição

**Content-Type:** `application/json`

```json
{
  "controleInterno": {
    "descricao": "Descrição dos controles identificados.",
    "nome": "Questionário de Avaliação de Controle Interno",
    "url": "https://exemplo.com/controle-interno"
  },
  "reuniao": {
    "descricao": "Resumo das informações obtidas na reunião.",
    "nome": "Ata de Reunião Preliminar",
    "url": "https://exemplo.com/reuniao"
  }
}
```

### Campos obrigatórios

Todos os seis campos são obrigatórios.

| Campo | Descrição |
|---|---|
| `controleInterno.descricao` | Descrição dos controles internos identificados. |
| `controleInterno.nome` | Nome do documento de controle interno. |
| `controleInterno.url` | Endereço do documento de controle interno. |
| `reuniao.descricao` | Resumo das informações obtidas na reunião. |
| `reuniao.nome` | Nome do documento da reunião. |
| `reuniao.url` | Endereço do documento da reunião. |

**Validação das URLs:** devem ser absolutas e utilizar os protocolos `http://` ou `https://`.

> **Importante:** os campos `nome` e `descricao` são enviados à inteligência artificial. Os campos `url` são utilizados exclusivamente pela aplicação para construir os hyperlinks na resposta, não sendo enviados ao modelo.

## Resposta de sucesso

**HTTP 200 — OK**

A API retorna o conteúdo gerado dividido em blocos ordenados.

```json
{
  "titulo": "Entendimento do Controle Interno",
  "blocos": [
    {
      "tipo": "TEXT",
      "texto": "A partir das informações disponibilizadas, identificou-se que a organização possui controles internos...",
      "url": null
    },
    {
      "tipo": "LINK",
      "texto": "Questionário de Avaliação de Controle Interno",
      "url": "https://exemplo.com/controle-interno"
    },
    {
      "tipo": "TEXT",
      "texto": "Adicionalmente, foram obtidas informações complementares durante a reunião preliminar...",
      "url": null
    },
    {
      "tipo": "LINK",
      "texto": "Ata de Reunião Preliminar",
      "url": "https://exemplo.com/reuniao"
    }
  ]
}
```

### Estrutura da resposta

| Propriedade | Descrição |
|---|---|
| `titulo` | Título da seção gerada. |
| `blocos` | Lista ordenada de elementos que compõem o conteúdo. |
| `blocos[].tipo` | Identifica o tipo do bloco: `TEXT` ou `LINK`. |
| `blocos[].texto` | Conteúdo textual ou título do hyperlink. |
| `blocos[].url` | Endereço do hyperlink. Para blocos `TEXT`, retorna `null`. |

### Integração com o frontend

Os blocos devem ser renderizados na ordem em que são recebidos.

- **TEXT:** renderizar o conteúdo de `texto` normalmente.
- **LINK:** renderizar `texto` como uma âncora HTML, utilizando `url` como destino.

Para hyperlinks, recomenda-se:

```html
<a
  [href]="bloco.url"
  target="_blank"
  rel="noopener noreferrer"
>
  {{ bloco.texto }}
</a>
```

**Atenção:** não tente identificar, extrair ou construir hyperlinks a partir do conteúdo textual. Utilize exclusivamente os blocos do tipo `LINK` retornados pela API.

## Tratamento de erros

Todas as respostas de erro seguem uma estrutura padronizada.

```json
{
  "timestamp": "2026-11-14T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Dados da requisição são inválidos.",
  "campos": {
    "controleInterno.url": "url deve ser uma URL válida."
  }
}
```

### Códigos HTTP

| Código | Descrição | Comportamento esperado no frontend |
|---|---|---|
| `400` | Requisição inválida ou campos ausentes. | Informar os erros de validação ao usuário. |
| `502` | O provedor de IA não retornou uma resposta utilizável. | Permitir que o usuário tente novamente. |
| `500` | Erro interno inesperado. | Exibir uma mensagem genérica de erro. |

O campo `campos` pode conter os detalhes dos erros de validação, permitindo identificar exatamente quais informações precisam ser corrigidas.

## Configuração

### 1. Chave de API Gemini

Configure uma chave válida do Google Gemini por meio da propriedade:

```properties
spring.ai.google.genai.api-key=${GEMINI_API_KEY}
```

Defina a variável de ambiente `GEMINI_API_KEY` com sua chave de API antes de iniciar a aplicação.

### 2. CORS

A aplicação permite, por padrão, requisições originadas dos seguintes endereços locais:

- `http://localhost:3000`
- `http://localhost:5173`

Para adicionar outras origens, configure a propriedade:

```properties
app.cors.allowed-origins=https://frontend.exemplo.com
```

É possível informar múltiplas origens separadas por vírgula:

```properties
app.cors.allowed-origins=http://localhost:3000,http://localhost:5173,https://frontend.exemplo.com
```

A configuração deve ser realizada antes de iniciar a aplicação.

## Executando localmente

Com as configurações e credenciais definidas, execute:

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

Após a inicialização, a API estará disponível em:

`http://localhost:8080`

## Fluxo de funcionamento

1. O frontend envia as descrições e os nomes dos documentos de controle interno e reunião.
2. A API valida os dados recebidos.
3. As informações textuais são utilizadas para construir o contexto e o prompt enviados ao modelo Gemini selecionado.
4. O modelo gera o conteúdo da seção Entendimento do Controle Interno.
5. A aplicação normaliza a resposta e organiza o conteúdo em blocos `TEXT` e `LINK`.
6. Os hyperlinks são construídos utilizando as URLs originais recebidas na requisição.
7. O frontend recebe a resposta estruturada e renderiza os blocos na ordem definida.

---

**Auditoria AI API** — Geração inteligente e estruturada de conteúdo para auditoria.