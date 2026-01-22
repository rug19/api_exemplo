package api.exemplo

class UrlMappings {

    static mappings = {

        "/"(controller: "home", action: "index")


        // Rotas RESTful para API

        //Aluno

        "/api/alunos"(controller: 'aluno') {
            action = [GET: "listar", POST: "criar"]
        }

        "/api/alunos/pesquisar"(controller: 'aluno') {
            action = [GET: "pesquisar"]
        }

        "/api/alunos/$id"(controller: 'aluno') {
            action = [GET: "listarPorId", PATCH: "atualizar", DELETE: "deletar"]
        }

        //Curso

        "/api/cursos"(controller: 'curso') {
            action = [GET: "listar", POST: "criar"]
        }

        "/api/cursos/$id"(controller: 'curso') {
            action = [GET: "listarPorId", PATCH: "atualizar", DELETE: "deletar"]
        }

        "/api/cursos/pesquisar"(controller: 'curso') {
            action = [GET: "pesquisar"]
        }

        //Matricula

        "/api/matriculas"(controller: 'matricula'){
            action = [GET: "listar", POST: "criar"]
        }

        "/api/matriculas/$id"(controller: 'matricula'){
            action = [GET: "listarPorId", DELETE: "deletar"]
        }


    }
}

