package api.exemplo.aluno


import grails.converters.JSON

import javax.xml.bind.ValidationException


class AlunoController {

    // Grails injeta o service automaticamente
    AlunoService alunoService

    static allowedMethods = [
            list  : "GET",
            getById: "GET",
            create  : "POST",
            update: ["PATCH"],
            delete: "DELETE"
    ]


    // Lista todos os alunos
    def list() {
        def alunos = alunoService.listStudents()
        render alunos as JSON

    }

    // Busca um aluno específico
    def getById(Long id) {
        try {
            def aluno = alunoService.getStudentById(id)
            response.status = 200
            render aluno as JSON
        } catch (IllegalArgumentException e){
            response.status = 404
            render([message: e.message] as JSON)

        }
    }

    //Cria um novo aluno
    def create() {
        try {
            def aluno = alunoService.createStudent(request.JSON)
            response.status = 201
            render aluno as JSON

        } catch (ValidationException e) {
            response.status = 400
            render([message: e.message] as JSON)
        }
    }

    //Atualiza um aluno existente
    def update(Long id) {
        try {
            def aluno = alunoService.updateStudent(id, request.JSON)
            response.status = 200
            render aluno  as JSON

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
            def aluno = alunoService.deleteStudent(id)
            response.status = 204
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)

        }

    }
}