package ucsal.cauzy.domain.utils.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName + " não encontrado. Identificador: " + identifier);
    }
}
