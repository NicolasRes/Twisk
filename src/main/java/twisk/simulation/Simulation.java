package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Monde;
import twisk.mondeIG.SujetObserve;
import twisk.outils.FabriqueNumero;
import twisk.outils.KitC;

/**
 * Classe Simulation qui simule le monde
 */
public class Simulation extends SujetObserve {
    private Monde monde;
    private KitC kitC;
    public native int[]start_simulation(int nbEtapes, int nbGuichets, int nbClients, int []tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();
    private int TMP_ATTENTE = 1;
    private int nbCLients;
    private GestionnaireClients gestionnaireClients;
    private String nomBibliotheque = "libTwisk";

    /**
     * Constructeur de la classe Simulation
     */
    public Simulation() {
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
        this.nbCLients = 0;
        this.gestionnaireClients = new GestionnaireClients();
    }

    /**
     * Méthode qui permet de lancer la simulation du monde
     * @param monde Le monde à simuler
     */
    public void simuler(Monde monde) {
        this.monde = monde;
        this.monde.setNbClients(this.nbCLients);
        String mondeC = monde.toC();

        int numSimulation = FabriqueNumero.getInstance().getNumeroSimulation();

        this.setNomBibliotheque("libTwisk" + numSimulation);
        this.kitC.creerFichier(mondeC);
        this.kitC.compiler();
        this.kitC.construireLabBibliotheque(this.nomBibliotheque);

        checkOSBibliotheque();

        System.out.println("==========Lancement de la simulation==========\n");
        lancerSimulation(this.monde);
    }

    /**
     * Méthode qui définit le nombre de clients du monde à simuler
     * @param nbClients
     */
    public void setNbClients(int nbClients) {
        this.nbCLients = nbClients;
    }

    /**
     * Méthode qui définit le nom de la bibliothèque
     * @param nom Le nom à assigner à la bibliothèque
     */
    public void setNomBibliotheque(String nom) {
        this.nomBibliotheque = nom;
    }

    /**
     * @brief Fonction qui affiche les informations relatives à la simulation
     *
     * @param nb_client Le nombre de clients
     * @param nb_guichet Le nombre de guichets
     * @param nb_etape Le nombre d'étapes
     */
    public void afficher_info_simu(int nb_client, int nb_guichet, int nb_etape , int[] tabJetonsGuichets) {
        System.out.println(monde.toString());

        System.out.println("nb client: " + nb_client);
        System.out.println("nb guichet: " + nb_guichet);
        System.out.println("nb etape: " + nb_etape);

        for (int i =0; i< nb_guichet; i++) {
            System.out.println("Jetons Guichet: " + (i+1)+ " " + tabJetonsGuichets[i]+" Jetons");
        }
        System.out.println();
    }

    /**
     * @brief Fonction qui affiche le PID d'un tableau de clients
     *
     * @param tabPid Le tableau de PID des clients
     * @param nb_client Le nombre de clients
     */
    public void afficher_pid_client(int[] tabPid, int nb_client) {
        System.out.print("Les clients: ");
        for (int i = 0; i < nb_client-1; i++) {
            System.out.print(tabPid[i] +" ");
        }
        System.out.println(tabPid[nb_client-1]);
        System.out.println();

    }

    /**
     * @brief Fonction qui exécute une simulation de parcours de clients dans un graphe
     *
     * @param nb_client Le nombre de clients de la simulation
     * @param nb_etape Le nombre d'étapes de la simulation
     */
    public void simule_clients(int nb_client, int nb_etape, Monde monde) {
        String[] nomEtapes = initialiserNomEtapes(monde, nb_etape);
        int[] position = ou_sont_les_clients(nb_etape, nb_client);

        while (position[(nb_client + 1)] < nb_client) {
            position = ou_sont_les_clients(nb_etape, nb_client);

            afficherClients(position, nomEtapes, monde, nb_client);
            notifierObservateurs();
            //afficherEtatGestionnaire();

            try {
                Thread.sleep(TMP_ATTENTE * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println();
        }

        System.out.println("==========Fin simulation==========\n");
        this.gestionnaireClients.nettoyer();
    }


    /**
     * Méthode qui lance la simulation du monde
     * @param monde Le monde à simuler
     */
    public void lancerSimulation(Monde monde){
        int nbEtapes = this.monde.nbEtapes();
        int nbGuichets = this.monde.nbGuichets();
        int nbClients = this.monde.nbClients();
        int[] tabJetonsGuichets = this.monde.getTabJetonsGuichets();

        afficher_info_simu(nbClients, nbGuichets, nbEtapes, tabJetonsGuichets);

        int[] tabPid = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichets);
        this.gestionnaireClients.setClients(tabPid);

        afficher_pid_client(tabPid, nbClients);
        simule_clients(nbClients, nbEtapes , this.monde);
        nettoyage();
    }

    /**
     * Méthode qui vérifie quel OS est utilisé pour la création du dossier tmp de la bibliothèque Twisk
     */
    private void checkOSBibliotheque() {
        if(System.getProperty("os.name").contains("Linux")) {
            System.load("/tmp/twisk/" + this.nomBibliotheque + ".so");
        } else if(System.getProperty("os.name").contains("Mac")) {
            System.load("/tmp/twisk/" + this.nomBibliotheque + ".dylib");
        }
    }

    /**
     * Méthode qui initialise le nom des étapes
     * @param monde Le Monde dans lequel initialisation les étapess
     * @param nb_etape Le nombre d'étapes
     * @return Le nom des étapes
     */
    private String[] initialiserNomEtapes(Monde monde, int nb_etape) {
        String[] nomEtapes = new String[nb_etape];
        for (Etape etape : monde) {
            nomEtapes[etape.getNumero()] = etape.getNom();
        }
        return nomEtapes;
    }

    /**
     * Méthode qui affiche les clients
     * @param position La position des clients
     * @param nomEtapes Le nom des étapes
     * @param monde Le monde dans lequel se trouvent les clients
     * @param nb_client Le nombre de clients
     */
    private void afficherClients(int[] position, String[] nomEtapes, Monde monde, int nb_client) {
        int nb_etape = nomEtapes.length;

        for (int i = 0; i < nb_etape; i++) {
            int nb_clients = position[i * (nb_client + 1)];
            System.out.print(nomEtapes[i] + ": ");

            for (int j = 0; j < nb_clients; j++) {
                System.out.print(position[i * (nb_client + 1) + 1 + j] + " ");
                this.gestionnaireClients.allerA(position[i * (nb_client + 1) + 1 + j], monde.getEtape(i), j);
            }
            System.out.println();
        }
    }

    /**
     * Méthode qui affiche l'état du gestionnaire
     */
    private void afficherEtatGestionnaire() {
        System.out.println("\nGestionnaire Clients :");
        for (Client c : this.gestionnaireClients) {
            if (c.getEtape() != null) {
                System.out.println("Client " + c.getNumeroClient() + " est à l'étape " + c.getEtape().getNom());
            }
        }
    }

    /**
     * Méthode qui renvoie le gestionnaire de clients
     * @return Le gestionnaire de clients
     */
    public GestionnaireClients getGestionnaireClients() {   // Ne pas supprimer, utilisée par introspection
        return this.gestionnaireClients;
    }

}