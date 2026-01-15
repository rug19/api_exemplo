package api.exemplo.curso

import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
class CursoService {

    Curso createCourse(Map dados){
        def curso = new Curso(dados)
        if(!curso.validate()){
            throw new ValidationException(
                    "Dados do curso invalido: ${curso.errors}"
            )
        }
        curso.save()
        return curso
    }

    List<Curso>listCourse(){
        return Curso.list()
    }

    Curso getCourseById(Long id){
        def curso = Curso.get(id)
        if(!curso){
            throw new IllegalArgumentException("Curso não encontrado pelo ID: ${id}")
        }
        return curso
    }

    Curso updateCourse(Long id, Map dados){
        def curso = Course.get(id)
        if(!curso) {
            throw new IllegalArgumentException("Curso não encontrado pelo ID: ${id}")
        }

        curso.properties = dados

        if(!curso.validate()){
            throw new ValidationException("Dados do curso invalido ${curso.errors}")
        }

        curso.save()
        return curso
    }
}
