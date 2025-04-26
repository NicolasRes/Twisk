package twisk.vues;

import javafx.scene.control.Alert;
import twisk.exceptions.MondeException;

/**
 * Classe utilitaire pour afficher les dialogues d'erreur
 */
public class DialogueErreur {

    /**
     * Affiche une fenêtre modale d'erreur avec le message de l'exception
     * @param exception L'exception à afficher
     */
    public static void afficherErreur(MondeException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(exception.getType().getMessage());
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }

}