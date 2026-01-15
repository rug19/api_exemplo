package api.exemplo

class UrlMappings {

    static mappings = {

        "/"(controller: "home", action: "index")


        // Rotas RESTful para API
        "/api/alunos"(controller: 'aluno') {
            action = [GET: "list", POST: "create"]
        }

        "/api/alunos/$id"(controller: 'aluno') {
            action = [GET: "getById", PATCH: "update", DELETE: "delete"]
        }


    }
}

