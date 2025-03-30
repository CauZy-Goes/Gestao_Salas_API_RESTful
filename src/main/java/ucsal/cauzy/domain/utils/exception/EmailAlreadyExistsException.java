package ucsal.cauzy.domain.utils.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException() {
        super("O email que você esta tentando registrar já existe");
    }
}
