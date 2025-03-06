package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

/**
 * Classe Simulation qui simule le monde
 */
public class Simulation {
    private KitC kitC;

    /**
     * Constructeur de la classe Simulation
     */
    public Simulation() {
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
    }

    /**
     * Méthode qui permet de lancer la simulation du monde
     * @param monde Le monde à simuler
     */
    public void simuler(Monde monde){
        //System.out.println(monde.toString());
        String mondeC = monde.toC();
        this.kitC.creerFichier(mondeC);
        this.kitC.compiler();
        //this.kitC.construireLabBibliotheque();
    }
}