package api.exemplo.aluno


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException


@Transactional
class AlunoService {

    //Cria um aluno
    Aluno createStudent(Map dados) {
        def aluno = new Aluno(dados)
        if (!aluno.validate()) {
            throw new ValidationException("Dados do aluno inválidos: ${aluno.errors}")
        }
        aluno.save()
        return aluno
    }


    List<Aluno> listStudents() {
        return Aluno.list()
    }


    Aluno getStudentById(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        return aluno
    }


    Aluno updateStudent(Long id, Map dados) {
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


    Aluno deleteStudent(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        aluno.delete()
    }
}
