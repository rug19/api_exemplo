package api.exemplo


import grails.converters.JSON

import javax.xml.bind.ValidationException


class AlunoController {

    AlunoService alunoService

    static allowedMethods = [
            listar     : "GET",
            listarPorId: "GET",
            pesquisar  : "GET",
            criar      : "POST",
            atualizar  : ["PATCH"],
            deletar    : "DELETE"
    ]


    def pesquisar() {
        try {
            def filtros = [
                    nome       : params.nome,
                    email      : params.email,
                    dataInicio : params.dataInicio,
                    dataFinal  : params.dataFinal
            ]

            def alunos = alunoService.pesquisar(filtros)
            response.status = 200
            render alunos as JSON
        } catch (Exception e) {
            response.status = 500
            render([message: "Erro ao pesquisar alunos: ${e.message}"] as JSON)
        }

    }


    def listar() {
        def alunos = alunoService.listarAlunos()
        render alunos as JSON

    }

    def listarPorId(Long id) {
        try {
            def aluno = alunoService.listarAlunoPorId(id)
            response.status = 200
            render aluno as JSON
        } catch (IllegalArgumentException e) {
            response.status = 404

            render([message: e.message] as JSON)

        }
    }

    def criar() {
        try {
            def aluno = alunoService.criarAluno(request.JSON)
            response.status = 201
            render aluno as JSON

        } catch (ValidationException e) {
            response.status = 400
            render([message: e.message] as JSON)
        }
    }

    def atualizar(Long id) {
        try {
            def aluno = alunoService.atualizarAluno(id, request.JSON)
            response.status = 200
            render aluno as JSON

        } catch (ValidationException e) {
            response.status = 400
            render([message: e.message] as JSON)
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)
        }

    }

    def deletar(Long id) {
        try {
            def aluno = alunoService.deletarAluno(id)
            response.status = 204
        } catch (IllegalArgumentException e) {
            response.status = 404
            render([message: e.message] as JSON)

        }

    }
}