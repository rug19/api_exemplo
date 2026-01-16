package api.exemplo

class Curso {
    Long id
    String titulo
    String descricao
    Integer cargaHoraria

    static constraints = {
        titulo nullable: false, blank: false, maxSize: 100
        descricao nullable: true, maxSize: 500
        cargaHoraria nullable: false, min: 1
    }
}

