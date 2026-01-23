package api.exemplo


import grails.converters.JSON

import javax.xml.bind.ValidationException

class CursoController {

    CursoService cursoService

    static allowedMethods = [
            listar     : "GET",
            pesquisar  : "GET",
            listarPorId: "GET",
            criar      : "POST",
            atualizar  : ["PATCH"],
            deletar    : "DELETE"
    ]

    def pesquisar() {
        try {
            def dados = [

                    titulo         : params.titulo,
                    descricao      : params.descricao,
                    cargaHoraria   : params.cargaHoraria,
                    cargaHorariaMin: params.cargaHorariaMin,
                    cargaHorariaMax: params.cargaHorariaMax,
                    page           : params.page,
                    max            : params.max
            ]

            def curso = cursoService.pesquisar(dados)
            response.status = 200
            render curso as JSON
        } catch (Exception e) {
            response.status = 500
            render([message: "Erro ao pesquisar curso: ${e.message}"] as JSON)
        }

    }


    def criar() {
        try {
            def curso = cursoService.criarCurso(request.JSON)
            response.status = 201
            render curso as JSON
        } catch (ValidationException e) {
            response.status = 400
            render([errors: e.errors] as JSON)
        }
    }

    def listar() {
        def curso = cursoService.listarCurso()
        render curso as JSON
    }

    def listarPorId(Long id) {
        try {
            def curso = cursoService.listarCursoPorId(id)
            response.status = 200
            render curso as JSON

        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        }
    }

    def atualizar(Long id) {
        try {
            def curso = cursoService.atualizarCurso(id, request.JSON)
            response.status = 200
            render curso as JSON
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        } catch (ValidationException e) {
            response.status = 400
            render([errors: e.errors] as JSON)
        }
    }

    def deletar(Long id) {
        try {
            def curso = cursoService.deletarCurso(id)
            response.status = 204
        } catch (IllegalArgumentException e) {
            render([message: e.message] as JSON)
        }
    }
}
