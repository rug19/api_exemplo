package api.exemplo.aluno

class Aluno {
    Long id
    String nome
    String email
    Date dataNascimento

    static constraints = {
        nome nullable: false, blank: false, size: 3..100
        email nullable: false, blank: false, email: true, unique: true, maxSize: 150
        dataNascimento nullable: false, validator: { date, obj ->
            if (date.after(new Date())) {
                return 'invalid.birthDate'
            }
        }
    }


    static mapping = {
        version false
        dataNascimento type: 'date'
    }
}


