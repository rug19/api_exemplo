package api.exemplo

import grails.converters.JSON

import javax.xml.bind.ValidationException

class MatriculaController {

    MatriculaService matriculaService

    static allowedMethods = [
            listar  : "GET",
            listarPorId: "GET",
            criar  : "POST",
            deletar: "DELETE"
    ]

    def criar() {
        try {
            def matricula = matriculaService.criarMatricula(request.JSON)
            response.status = 201
            render matricula as JSON

        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        } catch (ValidationException e) {
            response.status = 400
            render([errors: e.errors] as JSON)
        }

    }

    def listar() {
        def matricula = matriculaService.listarMatriculas()
        response.status = 200
        render matricula as JSON
    }

    def listarPorId(Long id) {
        try {
            def matricula = matriculaService.listarMatriculaPorId(id)
            response.status = 200
            render matricula as JSON
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        }

    }

    def deletar(Long id) {
        try {
            def matricula = matriculaService.deletarMatricula(id)
            response.status = 200
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message])
        }
    }

}
