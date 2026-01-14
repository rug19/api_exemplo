package api.exemplo.aluno


import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException


@Transactional
class AlunoService {


    Aluno salvarAluno(Map dados) {
        def aluno = new Aluno(dados)
        if (!aluno.validate()) {
            throw new ValidationException(
                "Dados do aluno inválidos: ${aluno.errors}"
            )
        }
        aluno.save()
        return aluno
    }


//    //Lista todos os alunos
//    List<Aluno> listarAlunos() {
//        return Aluno.list()
//    }
//
//
//    //Busca aluno por ID
//    Aluno buscarAluno(Long id) {
//        def aluno = Aluno.get(id)
//        if(!aluno){
//            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
//        } else {
//            return aluno
//        }
//    }


     //Atualiza um aluno existente
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


    //Deleta um aluno
    def deletarAluno(Long id) {
        def aluno = Aluno.get(id)
        if (!aluno) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: ${id}")
        }
        aluno.delete()
    }
}
