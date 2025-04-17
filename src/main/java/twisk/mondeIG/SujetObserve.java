package twisk.mondeIG;

import twisk.vues.Observateur;

import java.util.ArrayList;

/**
 * Classe qui permet la communication entre les observateurs et le modèle
 */
public class SujetObserve {
    private ArrayList<Observateur> obs = new ArrayList<>();

    /**
     * Méthode qui permet d'ajouter un Observateur à une liste
     * @param v L'observateur à ajouter
     */
    public void ajouterObservateur(Observateur v) {
        assert(v != null);
        this.obs.add(v);
    }

    /**
     * Méthode qui regroupe toutes les réactions des composants de l'interface graphique
     */
    public void notifierObservateurs() {
        for(Observateur o : obs) o.reagir();
    }
}