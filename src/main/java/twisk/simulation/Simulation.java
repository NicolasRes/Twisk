package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

/**
 * Classe Simulation qui simule le monde
 */
public class Simulation {
    private Monde monde;

    /**
     * Constructeur de la classe Simulation
     */
    public Simulation() {
        KitC kitC = new KitC();
        kitC.creerEnvironment();
    }

    /**
     * Méthode qui permet de lancer la simulation du monde
     * @param monde Le monde à simuler
     */
    public void simuler(Monde monde){
        System.out.println(monde.toString());
        monde.toC();
    }
}