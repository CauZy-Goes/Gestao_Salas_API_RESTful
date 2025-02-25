package ucsal.cauzy.domain.utils.exceptions;

public class DuplicateResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateResourceException(String resourceName, String field, String value) {
        super(resourceName + " já existe com " + field + ": " + value);
    }
}
