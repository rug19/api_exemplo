package api.exemplo.aluno


import grails.converters.JSON

import javax.xml.bind.ValidationException


class AlunoController {

    // Grails injeta o service automaticamente
    AlunoService alunoService

    static allowedMethods = [
            save  : "POST",
            update: "PUT",
            delete: "DELETE"
    ]


//    // Lista todos os alunos
//    def index() {
//        def alunos = alunoService.listarAlunos()
//        render alunos as JSON
//
//    }
//
//
//    // Busca um aluno específico
//    def show(Long id) {
//        def aluno = alunoService.buscarAluno(id)
//        if (!aluno) {
//            response.status = 404
//            render([message: e.message] as JSON)
//            return
//        }
//        render aluno as JSON
//    }


    //Cria um novo aluno
    def save() {
        try {
            def aluno = alunoService.salvarAluno(request.JSON)
            response.status = 201
            render aluno as JSON

        } catch (ValidationException e) {
            response.status = 400
            render([errors: e.errors] as JSON)
        }
    }


    //Atualiza um aluno existente
    def update(Long id) {

        try {
            render alunoService.atualizarAluno(id, request.JSON) as JSON

        } catch (ValidationException e) {
            response.status = 400
            render([errors: e.errors] as JSON)
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        }

    }


    //Deleta um aluno
    def delete(Long id) {
        try {
            render alunoService.deletarAluno(id) as JSON
            response.status = 204

        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)

        }

    }
}