package api.exemplo.aluno


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException


@Transactional
class AlunoService {


    Aluno createStudent(Map dados) {
        def aluno = new Aluno(dados)
        if (!aluno.validate()) {
            throw new ValidationException(
                "Dados do aluno inválidos: ${aluno.errors}"
            )
        }
        aluno.save()
        return aluno
    }


    //Lista todos os alunos
    List<Aluno> listStudents() {
        return Aluno.list()
    }


    //Busca aluno por ID
    Aluno getStudentById(Long id) {
        def aluno = Aluno.get(id)
        if(!aluno){
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        return aluno
    }


     //Atualiza um aluno existente
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


    //Deleta um aluno
    def deleteStudent(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        aluno.delete()
    }
}
