package api.exemplo

import grails.converters.JSON

import javax.xml.bind.ValidationException

class MatriculaController {

    MatriculaService matriculaService

    static allowedMethods = [
            listar  : "GET",
            listarPeloId: "GET",
            criar  : "POST",
            deletar: "DELETE"
    ]

    //CRIAR MATRICULA
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

    //LISTAR MATRICULA
    def listar() {
        def matricula = matriculaService.listarMatriculas()
        response.status = 200
        render matricula as JSON
    }

    //LISTAR MATRICULA POR ID
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

    //DELEATR UMA MATRICULA
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
