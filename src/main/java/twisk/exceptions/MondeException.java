package twisk.exceptions;

/**
 * Classe d'exception pour les erreurs liées au monde de simulation
 */
public class MondeException extends RuntimeException {
    /**
     * Type d'erreur
     */
    private final TypeErreur type;

    /**
     * Constructeur avec message d'erreur et type spécifique
     * @param message Le message d'erreur
     * @param type Le type d'erreur
     */
    public MondeException(String message, TypeErreur type) {
        super(message);
        this.type = type;
    }

    /**
     * Retourne le type d'erreur
     * @return Le type d'erreur
     */
    public TypeErreur getType() {
        return type;
    }

    /**
     * Énumération des différents types d'erreurs possibles
     */
    public enum TypeErreur {
        MONDE_VIDE("Le monde ne contient aucune étape"),
        AUCUNE_ENTREE("Le monde n'a aucune entrée"),
        AUCUNE_SORTIE("Le monde n'a aucune sortie"),
        SUCCESSEUR_GUICHET_INVALIDE("Le successeur d'un guichet ne peut pas être un guichet"),
        NOMBRE_SUCCESSEURS_GUICHET("Un guichet doit avoir exactement un successeur"),
        SUCCESSEUR_NON_TROUVE("Successeur non trouvé"),
        ERREUR_NON_ENTREE_NON_PRED("Une activité sans prédécesseur doit être déclarée comme entrée"),
        ERREUR_NON_SORTIE_NON_SUCC("Une activité sans successeur doit être déclarée comme sortie"),
        ERREUR_SORTIE_NON_VIDE("Une sortie ne doit pas avoir de successeur"),
        ERREUR_ACTIVITE_RESTREINTE_ENTREE("Une activité restreinte ne doit pas être une entrée");

        private final String message;

        TypeErreur(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}