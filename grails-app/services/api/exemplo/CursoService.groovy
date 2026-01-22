package api.exemplo


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class CursoService {

    Curso criarCurso(Map dados) {
        def curso = new Curso(dados)
        if (!curso.validate()) {
            throw new ValidationException(
                    "Dados do curso invalido: ${curso.errors}"
            )
        }
        curso.save()
        return curso
    }

    Map pesquisar(Map dados) {

        def max = dados.max ? dados.max.toInteger() : 10
        def page = dados.page ? dados.page.toInteger() : 1
        def offset = (page - 1) * max


        def criteria = Curso.createCriteria()

        def resultado = criteria.list(max: max, offset: offset) {

            if (dados.titulo) {
                ilike("titulo", "%${dados.titulo}%")
            }

            if (dados.descricao) {
                ilike("descricao", "%${dados.descricao}%")
            }

            if (dados.cargaHoraria) {
                eq("cargaHoraria", dados.cargaHoraria.toInteger())

            }

            if (dados.cargaHorariaMin || dados.cargaHorariaMax) {
                and {
                    if (dados.cargaHorariaMin) {
                        ge("cargaHoraria", dados.cargaHorariaMin.toInteger())

                    }

                    if (dados.cargaHorariaMax) {
                        le("cargaHoraria", dados.cargaHorariaMax.toInteger())
                    }

                }
            }
        }

        return [
                dados    : resultado ?: [],
                paginacao: [
                        paginaAtual   : page,
                        itensPorPagina: max
                ]
        ]

    }

//    List<Curso> listarCurso() {
//        return Curso.list()
//    }

    Curso listarCursoPorId(Long id) {
        def curso = Curso.get(id)
        if (!curso) {
            throw new IllegalArgumentException("Curso não encontrado pelo ID: ${id}")
        }
        return curso
    }

    Curso atualizarCurso(Long id, Map dados) {
        def curso = Curso.get(id)
        if (!curso) {
            throw new IllegalArgumentException("Curso não encontrado pelo ID: ${id}")
        }

        curso.properties = dados

        if (!curso.validate()) {
            throw new ValidationException("Dados do curso invalido ${curso.errors}")
        }

        curso.save()
        return curso
    }

    Curso deletarCurso(Long id) {
        def curso = Curso.get(id)
        if (!curso) {
            throw new IllegalArgumentException("Curso não encontrado pelo id ${id}")
        }
        curso.delete()
    }
}
