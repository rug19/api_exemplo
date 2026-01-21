package api.exemplo


import grails.gorm.transactions.Transactional

import javax.swing.CellEditor
import javax.xml.bind.ValidationException


@Transactional
class AlunoService {

    List<Aluno> pesquisar(Map filtros) {
        def resultado = Aluno.createCriteria().list {
            if (filtros.nome) {
                ilike("nome", "%${filtros.nome}%")
            }
            if (filtros.email) {
                eq("email", "${filtros.email}")
            }

            if (filtros.dataInicio && filtros.dataFinal) {

                def formato = new java.text.SimpleDateFormat('dd/MM/yyyy')
                Date dataInicio = formato.parse(filtros.dataInicio.toString())
                Date dataFinal = formato.parse(filtros.dataFinal.toString())

                between('dataNascimento', dataInicio, dataFinal)

            }

            order('nome', 'asc')

        }
        return resultado ?: []
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


//    Aluno listarAlunoPorId(Long id) {
//        def aluno = Aluno.get(id)
//        if (!aluno) {
//            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
//        }
//        return aluno
//    }


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
