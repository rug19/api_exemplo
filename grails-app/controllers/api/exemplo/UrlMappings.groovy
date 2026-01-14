package api.exemplo

class UrlMappings {

    static mappings = {

        "/"(controller: "home", action: "index")


        // Rotas RESTful para API
        "/api/alunos"(controller: 'aluno.aluno') {
            action = [GET: "index", POST: "save"]
        }

        "/api/alunos/$id"(controller: 'aluno.aluno') {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
        }


    }
}

