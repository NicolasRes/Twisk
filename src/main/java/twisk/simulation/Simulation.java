package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

/**
 * Classe Simulation qui simule le monde
 */
public class Simulation {
    private Monde monde;
    private KitC kitC;
    public native int[]start_simulation(int nbEtapes, int nbGuichets, int nbClients, int []tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

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
        this.monde = monde;
        //System.out.println(monde.toString());
        String mondeC = monde.toC();
        this.kitC.creerFichier(mondeC);
        this.kitC.compiler();
        this.kitC.construireLabBibliotheque();
        if(System.getProperty("os.name").contains("Linux")){
            System.load("/tmp/twisk/libTwisk.so");
        }
        else if(System.getProperty("os.name").contains("Mac")){
            System.load("/tmp/twisk/libTwisk.dylib");
        }

        //ajouter main en dessous

        lancerSimulation();


    }

    public void lancerSimulation(){

        int nbEtapes = this.monde.nbEtapes();

    }
}