package twisk.exceptions;

/**
 * Exception spécifique aux erreurs liées aux jetons
 */
public class TwiskJetonsException extends RuntimeException {
    public TwiskJetonsException(String message) {
        super(message);
    }
}
