package api.exemplo


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException


@Transactional
class AlunoService {

    Map pesquisar(Map filtros) {

        def max = filtros.max ? filtros.max.toInteger() : 10
        def page = filtros.page ? filtros.page.toInteger() : 1
        def offset = (page - 1) * max

        Date dataInicio = null
        Date dataFinal = null

        if (filtros.dataInicio && filtros.dataFinal) {

            def formato = new java.text.SimpleDateFormat('dd/MM/yyyy')
            dataInicio = formato.parse(filtros.dataInicio.toString())
            dataFinal = formato.parse(filtros.dataFinal.toString())

        }

        def criteria = Aluno.createCriteria()

        def resultado = criteria.list(max: max, offset: offset) {
            if (filtros.nome) {
                ilike("nome", "%${filtros.nome}%")
            }
            if (filtros.email) {
                eq("email", filtros.email)
            }

            if (dataInicio && dataFinal) {
                between('dataNascimento', dataInicio, dataFinal)

            }


        }
        return [
                dados: resultado ?: [],
                paginacao: [
                        paginaAtual: page,
                        itensPorPagina: max,
                ]
        ]
    }


    Aluno criarAluno(Map dados) {
        def aluno = new Aluno(dados)
        if (!aluno.validate()) {
            throw new ValidationException("Dados do aluno inválidos: ${aluno.errors}")
        }
        aluno.save()
        return aluno
    }


//    List<Aluno> listarAlunos() {
//        return Aluno.list()
//    }


    Aluno listarAlunoPorId(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        return aluno
    }


    Aluno atualizarAluno(Long id, Map dados) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        aluno.properties = dados
        if (!aluno.validate()) {
            throw new ValidationException("Dados do aluno inválidos: ${aluno.errors}")
        }
        aluno.save()
        return aluno
    }


    Aluno deletarAluno(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        aluno.delete()
    }
}
