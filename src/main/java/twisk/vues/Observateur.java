package twisk.vues;

/**
 * Interface qui regroupe les vues du jeu et assure la communication avec le modèle
 */
public interface Observateur {

    /**
     * Méthode qui met à jour les informations des observateurs dans l'interface graphique
     */
    void reagir();
}