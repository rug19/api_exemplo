package api.exemplo

class Curso {
    Long id
    String titulo
    String descricao
    Integer cargaHoraria

    static hasMany = [matricula: Matricula]

    static constraints = {
        titulo nullable: false, blank: false, maxSize: 100, unique: true
        descricao nullable: true, maxSize: 500
        cargaHoraria nullable: false, min: 1, max:9999
    }

    static mapping = {
        version: false

    }

}

