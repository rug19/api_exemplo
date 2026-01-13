
class Aluno {
    Long id
    String nome
    String email
    Date dataNascimento

    static constraints = {
        nome nullable: false, blank: false
        email nullable:false, black: false, email: true
        dataNascimento nullable: false

    }

}

