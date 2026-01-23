package api.exemplo

import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class MatriculaService {

    Matricula criarMatricula(Map dados) {

        Aluno aluno = Aluno.get(dados.aluno.id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado")
        }

        Curso curso = Curso.get(dados.curso.id)
        if (!curso) {
            throw new IllegalArgumentException("Curso não encontrado")
        }

        def matricula = new Matricula(
                aluno: aluno,
                curso: curso,
                dataMatricula: dados.dataMatricula,
                valorPago: dados.valorPago

        )

        if (!matricula.validate()) {
            throw new ValidationException("Dados da matricula invalidos")
        }
        matricula.save()
        return matricula
    }


//    List<Matricula> listarMatriculas() {
//        return Matricula.list()
//    }

    Map pesquisar(Map dados) {

        def max = dados.max ? dados.max.toInteger() : 10
        def page = dados.page ? dados.page.toInteger() : 1
        def offset = (page - 1) * max

        Date dataMatriculaInicio = null
        Date dataMatriculaFim = null

        if (dados.dataMatriculaInicio && dados.dataMatriculaFim) {

            def formato = new java.text.SimpleDateFormat('dd/MM/yyyy')
            dataMatriculaInicio = formato.parse(dados.dataMatriculaInicio.toString())
            dataMatriculaFim = formato.parse(dados.dataMatriculaFim.toString())

        }

        def criteria = Matricula.createCriteria()

        def resultado = criteria.list(max: max, offset: offset) {

            if (dataMatriculaInicio && dataMatriculaFim) {
                between('dataMatricula', dataMatriculaInicio, dataMatriculaFim)
            }

            if (dados.valorPagoMin || dados.valorPagoMax) {
                and {
                    if (dados.valorPagoMin) {
                        ge("valorPago", dados.valorPagoMin.toBigDecimal())
                    }

                    if (dados.valorPagoMax) {
                        le("valorPago", dados.valorPagoMax.toBigDecimal())
                    }
                }
            }
        }

        return [
                dados: resultado ?: [],
                paginacao: [
                        paginaAtual: page,
                        itensPorPagina: max
                ]
        ]
    }


    Matricula listarMatriculaPorId(Long id) {
        def matricula = Matricula.get(id)
        if (!matricula) {
            throw new IllegalArgumentException("Matricula não encontrada")
        }
        return matricula
    }

    Matricula deletarMatricula(Long id) {
        def matricula = Matricula.get(id)
        if (!matricula) {
            throw new IllegalArgumentException("Matricula não encontrada")
        }

        matricula.delete()
        return matricula
    }


}
