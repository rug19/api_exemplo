package api.exemplo


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
            render([errors: e.errors] as JSON)
        }
    }

    def list(){
        def curso = cursoService.listCourse()
        render curso as JSON
    }

    def getById(Long id){
        try {
            def curso = cursoService.getCourseById(id)
            response.status = 200
            render curso as JSON

        } catch (IllegalArgumentException e){
            response.status = 404
            render([message: e.message] as JSON)
        }
    }

    def update(Long id){
        try {
            def curso = cursoService.updateCourse(id, request.JSON)
            response.status = 200
            render curso as JSON
        } catch(IllegalArgumentException e){
            response.status = 404
            render([message: e.message] as JSON)
        }catch(ValidationException e){
            response.status = 400
            render([errors: e.errors] as JSON)
        }
    }

    def delete(Long id){
        try {
            def curso = cursoService.deleteCourse(id)
            response.status = 204
        } catch (IllegalArgumentException e){
            render([message: e.message] as JSON)
        }
    }
}
