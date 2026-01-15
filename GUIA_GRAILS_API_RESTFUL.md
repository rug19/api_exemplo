# 📚 Guia Completo: Grails API RESTful

## 🎯 **Respondendo suas Dúvidas**

### **1. `def` no Groovy**
```groovy
def salvarAluno(Map params) {
    def aluno = new api.exemplo.aluno.Aluno(params)
    // ...
}
```

- `def` = tipagem dinâmica (como `var` no JavaScript)
- Você pode usar `def` OU especificar o tipo:
  - `def aluno = new api.exemplo.aluno.Aluno()` ✅
  - `api.exemplo.aluno.Aluno aluno = new api.exemplo.aluno.Aluno()` ✅ (mais recomendado para clareza)

### **2. `Map params` - O que é?**
```groovy
// Map é como um JSON/Dicionário:
Map params = [
    nome: "João Silva",
    email: "joao@email.com",
    dataNascimento: new Date()
]

// Quando você faz:
def aluno = new api.exemplo.aluno.Aluno(params)
// Grails automaticamente mapeia as chaves para as propriedades do objeto
```

**De onde vem o `params`?**
- No Controller, vem do `request.JSON` (corpo da requisição HTTP)
- Exemplo de JSON recebido:
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "dataNascimento": "2000-01-15"
}
```

### **3. `validate()` - Para que serve?**
```groovy
if (!aluno.validate()) {
    throw new IllegalArgumentException("Dados inválidos: ${aluno.errors}")
}
```

O `validate()` verifica as **constraints** definidas no Domain:

**No api.exemplo.aluno.Aluno.groovy:**
```groovy
static constraints = {
    nome nullable: false, blank: false        // Obrigatório e não pode ser vazio
    email nullable: false, blank: false, email: true  // Obrigatório, não vazio e formato de email
    dataNascimento nullable: false            // Obrigatório
}
```

**Exemplos práticos:**
- ✅ `nome: "João"` → válido
- ❌ `nome: null` → inválido (nullable: false)
- ❌ `nome: ""` → inválido (blank: false)
- ✅ `email: "joao@email.com"` → válido
- ❌ `email: "joao"` → inválido (email: true)

### **4. `nullable` nas Constraints**
```groovy
nome nullable: false     // Campo obrigatório (não aceita null)
telefone nullable: true  // Campo opcional (pode ser null)
```

---

## 🏗️ **Arquitetura Grails RESTful - Estrutura de Pastas**

### **✅ Estrutura Correta (Boas Práticas)**

```
grails-app/
├── domain/
│   └── api.exemplo.aluno.Aluno.groovy                         ← SEM package (ou package simples)
│
├── services/
│   └── api.exemplo.aluno.AlunoService.groovy                  ← SEM package (Grails encontra automaticamente)
│
├── controllers/
│   └── api/
│       └── exemplo/
│           ├── AlunoController.groovy       ← COM package api.exemplo
│           └── UrlMappings.groovy           ← COM package api.exemplo
```

### **📦 Quando usar package `api.exemplo`?**

**✅ USE package nos Controllers:**
```groovy
package api.exemplo

class AlunoController {
    // ...
}
```

**✅ USE package no UrlMappings:**
```groovy
package api.exemplo

class UrlMappings {
    // ...
}
```

**❌ NÃO USE package em Domain e Services:**
```groovy
// api.exemplo.aluno.Aluno.groovy
class api.exemplo.aluno.Aluno {
    // SEM package aqui!
}

// api.exemplo.aluno.AlunoService.groovy
class api.exemplo.aluno.AlunoService {
    // SEM package aqui!
}
```

**Por quê?**
- **Controllers**: O package organiza suas rotas da API (`/api/exemplo/...`)
- **Domain e Services**: Grails procura eles automaticamente em qualquer lugar da pasta

---

## 🔄 **Arquitetura em Camadas - Como Funciona**

```
Cliente (Postman/Frontend)
    ↓
    ↓ HTTP Request (JSON)
    ↓
┌────────────────────────┐
│   AlunoController      │  ← Recebe requisição HTTP
│   (Camada de API)      │    Valida entrada
└────────────────────────┘    Retorna JSON
    ↓
    ↓ chama método
    ↓
┌────────────────────────┐
│   api.exemplo.aluno.AlunoService         │  ← Lógica de negócio
│   (Camada de Negócio)  │    Validações
└────────────────────────┘    Transações
    ↓
    ↓ acessa banco
    ↓
┌────────────────────────┐
│   api.exemplo.aluno.Aluno (Domain)       │  ← Modelo de dados
│   (Camada de Dados)    │    GORM (ORM do Grails)
└────────────────────────┘    Banco de Dados
```

### **Exemplo Prático:**

**1. Cliente faz requisição POST:**
```bash
POST http://localhost:8080/api/alunos
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "dataNascimento": "2000-01-15"
}
```

**2. Controller recebe e passa para Service:**
```groovy
// AlunoController.groovy
def save() {
    def aluno = alunoService.salvarAluno(request.JSON)  // request.JSON é o Map
    render aluno as JSON
}
```

**3. Service processa e salva:**
```groovy
// api.exemplo.aluno.AlunoService.groovy
api.exemplo.aluno.Aluno salvarAluno(Map params) {
    def aluno = new api.exemplo.aluno.Aluno(params)  // Cria objeto com os dados do Map
    if (!aluno.validate()) {        // Valida constraints
        throw new IllegalArgumentException("Dados inválidos")
    }
    aluno.save(flush: true)         // Salva no banco
    return aluno
}
```

**4. Domain define estrutura:**
```groovy
// api.exemplo.aluno.Aluno.groovy
class api.exemplo.aluno.Aluno {
    String nome
    String email
    Date dataNascimento
    
    static constraints = {
        nome nullable: false, blank: false
        email nullable: false, email: true
        dataNascimento nullable: false
    }
}
```

---

## 🌐 **UrlMappings - Precisa?**

### **Para API RESTful: SIM, mas pode ser simplificado!**

**Forma Atual (mais RESTful):**
```groovy
static mappings = {
    "/api/alunos"(controller: 'aluno') {
        action = [GET: "index", POST: "save"]
    }
    
    "/api/alunos/$id"(controller: 'aluno') {
        action = [GET: "show", PUT: "update", DELETE: "delete"]
    }
}
```

**Isso cria as rotas:**
- `GET /api/alunos` → lista todos
- `POST /api/alunos` → cria novo
- `GET /api/alunos/1` → busca ID 1
- `PUT /api/alunos/1` → atualiza ID 1
- `DELETE /api/alunos/1` → deleta ID 1

### **Alternativa: Usar recursos REST do Grails**
```groovy
static mappings = {
    "/api/alunos"(resources: 'aluno')  // Cria TODAS as rotas REST automaticamente
}
```

---

## 📝 **Exemplo Completo de Uso**

### **Testando a API (com Postman ou curl):**

```bash
# 1. Criar aluno
POST http://localhost:8080/api/alunos
{
  "nome": "Maria Santos",
  "email": "maria@email.com",
  "dataNascimento": "1995-03-20"
}

# 2. Listar todos
GET http://localhost:8080/api/alunos

# 3. Buscar específico
GET http://localhost:8080/api/alunos/1

# 4. Atualizar
PUT http://localhost:8080/api/alunos/1
{
  "nome": "Maria Santos Silva",
  "email": "maria.santos@email.com"
}

# 5. Deletar
DELETE http://localhost:8080/api/alunos/1
```

---

## ✅ **Checklist de Boas Práticas**

- ✅ **Domain**: Sem package, apenas classe com constraints
- ✅ **Service**: Sem package, com `@Transactional`
- ✅ **Controller**: Com package `api.exemplo`, retorna JSON
- ✅ **UrlMappings**: Define rotas REST explícitas
- ✅ **Validações**: Use `validate()` antes de salvar
- ✅ **Tratamento de erros**: Try-catch com status HTTP apropriados
- ✅ **Injeção de dependência**: Grails injeta o Service automaticamente

---

## 🎓 **Resumo das Suas Dúvidas**

| Dúvida | Resposta |
|--------|----------|
| **O que é `def`?** | Tipagem dinâmica do Groovy (como `var` em Java/JS) |
| **O que é `Map params`?** | Dicionário chave-valor que vem do JSON da requisição |
| **Para que serve `validate()`?** | Valida o objeto contra as constraints do Domain |
| **O que é `nullable`?** | Define se o campo é obrigatório (false) ou opcional (true) |
| **Precisa do UrlMappings?** | Sim, para definir rotas REST personalizadas |
| **Controller no package `api.exemplo`?** | Sim, para organizar a API |
| **Domain precisa de package?** | Não, Grails encontra automaticamente |
| **Service precisa de package?** | Não, Grails encontra automaticamente |

---

## 🚀 **Próximos Passos**

1. ✅ Estrutura criada e corrigida
2. 🧪 Teste a API com Postman ou curl
3. 📊 Adicione mais validações conforme necessário
4. 🔐 Considere adicionar autenticação (Spring Security)
5. 📝 Documente sua API (Swagger/OpenAPI)

