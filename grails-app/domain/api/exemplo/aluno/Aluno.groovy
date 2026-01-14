package api.exemplo.aluno

class Aluno {
    Long id
    String nome
    String email
    Date dataNascimento

    static constraints = {
        nome nullable: false, blank: false
        email nullable: false, blank: false, email: true
        dataNascimento nullable: false
    }

    static mapping = {
        version false  // Remove controle de versão se não precisar
    }
}

