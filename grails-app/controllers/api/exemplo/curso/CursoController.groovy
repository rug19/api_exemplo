package api.exemplo.curso

import grails.converters.JSON

import javax.xml.bind.ValidationException

class CursoController {

    CursoService cursoService

    static allowedMethods = [
        list  : "GET",
        getById: "GET",
        create  : "POST",
        update: ["PATCH"],
        delete: "DELETE"
    ]

    def create(){
        try {
            def curso = cursoService.createCourse(request.JSON)
            response.status = 201
            render curso as JSON
        } catch(ValidationException e){
            response.status = 400
            render([message: e.message] as JSON)
        }
    }
}
