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


    List<Matricula> listarMatriculas() {
        return Matricula.list()
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
