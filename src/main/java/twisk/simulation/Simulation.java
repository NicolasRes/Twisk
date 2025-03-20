package twisk.simulation;

import twisk.monde.Monde;
import twisk.outil.KitC;

/**
 * Classe Simulation qui simule le monde
 */
public class Simulation {
    private Monde monde;
    private KitC kitC;
    public native int[]start_simulation(int nbEtapes, int nbGuichets, int nbClients, int []tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();
    private int TMP_ATTENTE = 1;
    private int nbCLient;


    /**
     * Constructeur de la classe Simulation
     */
    public Simulation() {
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
        this.nbCLient=0;
    }

    public void setNbClients(int nbClients) {
        this.nbCLient=nbClients;
    }

    /**
     * Méthode qui permet de lancer la simulation du monde
     * @param monde Le monde à simuler
     */
    public void simuler(Monde monde){

        this.monde = monde;
        this.monde.setNbClients(nbCLient);
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

        System.out.println("Lancement de la simulation");
        lancerSimulation();

    }

    public void afficher_info_simu(int nb_client, int nb_guichet, int nb_etape , int[] tabJetonsGuichets) {
        System.out.println("nb client: " + nb_client);
        System.out.println("nb guichet: " + nb_guichet);
        System.out.println("nb etape: " + nb_etape);
        for (int i =0; i< nb_guichet; i++) {
            System.out.println("Jetons Guichet: " + (i+1)+ " " + tabJetonsGuichets[i]+" Jetons");
        }
    }

    public void afficher_pid_client(int[] tabPid, int nb_client) {
        System.out.println("Les clients: ");
        for (int i = 0; i < nb_client-1; i++) {
            System.out.println(tabPid[i]);
        }
        System.out.println(tabPid[nb_client-1]);
    }

    public void simule_clients(int nb_client, int nb_etape) {
        int[]position = ou_sont_les_clients(nb_etape,nb_client);

        while (position[(nb_client + 1)] < nb_client) { //  Tant que tous les clients ne sont pas dans la dernière activité, nbact-1 car on commence à 0
            position = ou_sont_les_clients(nb_etape, nb_client);
            for (int i = 0; i < nb_etape; i++) {
                int nb_clients = position[i * (nb_client + 1)];
                System.out.print("Étape " + i + " : " + nb_clients + " clients :"); // Pas de println ici

                for (int j = 0; j < nb_clients; j++) {
                    System.out.print(" " + position[i * (nb_client + 1) + 1 + j]); // Affichage sur la même ligne
                }

                System.out.println(); // Saut de ligne après chaque étape
            }

            try{
                Thread.sleep(TMP_ATTENTE*1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println();


        }
        System.out.println("Fin simulation");
    }


    public void lancerSimulation(){

        int nbEtapes = this.monde.nbEtapes();
        int nbGuichets = this.monde.nbGuichets();
        int nbClients = this.monde.nbClients();
        int[] tabJetonsGuichets = this.monde.getTabJetonsGuichets();


        afficher_info_simu(nbClients, nbGuichets, nbEtapes, tabJetonsGuichets);

        int[] tabPid = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichets);

        afficher_pid_client(tabPid, nbClients);
        simule_clients(nbClients, nbEtapes);
        nettoyage();

    }
}