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

    def pesquisar() {
        try {

            def dados = [
                    dataMatriculaInicio: params.dataMatriculaInicio,
                    dataMatriculaFim   : params.dataMatriculaFim,
                    valorPagoMin      : params.valorPagoMin,
                    valorPagoMax      : params.valorPagoMax,
                    page               : params.page,
                    max                : params.max
            ]
             def matriculas = matriculaService.pesquisar(dados)
                response.status = 200
                render matriculas as JSON
        } catch (Exception e) {
            response.status = 500
            render([message: "Erro ao pesquisar matrículas: ${e.message}"] as JSON)
        }

    }

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
        def matriculas = matriculaService.listarMatriculas()
        def resultado = matriculas.collect { matricula ->
            [
                    id: matricula.id,
                    dataMatricula: matricula.dataMatricula,
                    valorPago: matricula.valorPago,
                    aluno: [
                            id: matricula.aluno.id,
                            nome: matricula.aluno.nome,
                            email: matricula.aluno.email,
                            dataNascimento: matricula.aluno.dataNascimento
                    ],
                    curso: [
                            id: matricula.curso.id,
                            titulo: matricula.curso.titulo,
                            descricao: matricula.curso.descricao,
                            cargaHoraria: matricula.curso.cargaHoraria
                    ]
            ]
        }
        response.status = 200
        render resultado as JSON
    }

    def listarPorId(Long id) {
        try {
            def matricula = matriculaService.listarMatriculaPorId(id)

            def resultado = [
                    id: matricula.id,
                    dataMatricula: matricula.dataMatricula,
                    valorPago: matricula.valorPago,
                    aluno: [
                            id: matricula.aluno.id,
                            nome: matricula.aluno.nome,
                            email: matricula.aluno.email,
                            dataNascimento: matricula.aluno.dataNascimento
                    ],
                    curso: [
                            id: matricula.curso.id,
                            titulo: matricula.curso.titulo,
                            descricao: matricula.curso.descricao,
                            cargaHoraria: matricula.curso.cargaHoraria
                    ]
            ]
            response.status = 200
            render resultado as JSON
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
