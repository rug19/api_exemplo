package api.exemplo


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException


@Transactional
class AlunoService {


    Aluno criarAluno(Map dados) {
        def aluno = new Aluno(dados)
        if (!aluno.validate()) {
            throw new ValidationException("Dados do aluno inválidos: ${aluno.errors}")
        }
        aluno.save()
        return aluno
    }


    List<Aluno> listarAlunos() {
        return Aluno.list()
    }


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
