package api.exemplo

class Matricula {
    Long id
    Aluno aluno
    Curso curso
    Date dataMatricula
    BigDecimal valorPago


    static belongsTo = [aluno: Aluno, curso: Curso]

    static constraints = {
        aluno nullable: false
        curso nullable: false
        dataMatricula nullable: false
        valorPago min: 0.0
        dataMatricula nullable: false

    }

    static mapping = {
        version: false
        dataMatricula type: 'date'

    }

}