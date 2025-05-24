package twisk.mondeIG;

import javafx.application.Platform;
import javafx.concurrent.Task;
import twisk.ClientTwisk;
import twisk.exceptions.MondeException;
import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.ThreadsManager;
import twisk.vues.Observateur;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe qui gère la simulation de l'interface graphique
 */
public class SimulationIG implements Observateur {
    private final MondeIG mondeIG;
    private CorrespondancesEtapes correspondance;
    private int nbClients;
    private HashMap<Integer, Integer> couleursClients;

    private static Object instance;
    private static ClassLoaderPerso clp;
    private static Method setNbClient;
    private static Method simuler;

    /**
     * Constructeur de la classe SimulationIG
     * @param monde Le monde à simuler
     */
    public SimulationIG(MondeIG monde) {
        assert(monde != null);
        this.mondeIG = monde;
        this.couleursClients = new HashMap<>();
    }

    /**
     * Méthode qui lance une simulation à partir d'un Monde créé selon un MondeIG valide
     */
    public void simuler() {
        verifierMondeIG();
        Monde monde = creerMonde();

        // On lance la simulation dans un nouveau thread
        Task<Void> simulation = new Task<>() {
            @Override
            protected Void call() throws Exception {
                instrospectionSimu(monde);
                return null;
            }
        };

        ThreadsManager.getInstance().lancer(simulation);
        System.gc();
    }

    /**
     * Méthode qui vérifie que les conditions de validité du Monde à simuler sont bien remplies
     * @throws MondeException Si le monde n'est pas valide
     */
    private void verifierMondeIG() throws MondeException {
        boolean aEntree = false;
        boolean aSortie = false;

        // Ajouter vérif  bouton fin simu + lois exponentielles + bitcoin à envoyer à Nico

        if (this.mondeIG.getEtapes().isEmpty()) {
            throw new MondeException("Le monde n'a aucune étape", MondeException.TypeErreur.MONDE_VIDE);
        }

        for (EtapeIG e : this.mondeIG) {
            if (e.getType().equals("Guichet")) {
                verifierGuichet(e);
                verifActiviteRestreintePasEntree(e);
            }
            if (e.estEntree()) {
                aEntree = true;
            }
            if (e.estSortie()) {
                aSortie = true;
            }
            verifEntreePrede(e);
            verifSortieSucc(e);
            verifierLois(e);

        }

        if (!aEntree) {
            throw new MondeException("Aucune entrée n'a été définie dans le monde", MondeException.TypeErreur.AUCUNE_ENTREE);
        }
        if (!aSortie) {
            throw new MondeException("Aucune sortie n'a été définie dans le monde", MondeException.TypeErreur.AUCUNE_SORTIE);
        }
    }

    private void verifierLois(EtapeIG e) {
        if (e.estEntree() && e.getLois().equals("gaussienne")){
            throw new MondeException("L'activité "+ e.getNom() +" est une entrée et sa lois ne peut pas être gaussienne", MondeException.TypeErreur.PB_LOIS_ENTREE);
        }
        if( !e.estEntree() && e.getLois().equals("exponentielle")){
            throw new MondeException("L'activité "+ e.getNom() +" est n'est pas une entrée donc sa lois ne peut pas être exponentielle", MondeException.TypeErreur.PB_LOIS_ACTIVITE_EXPO);
        }
    }

    /**
     * Méthode qui vérifie si une étape est bien mise en entrée si elle n'a aucun prédécesseur
     * @param e L'étape à vérifier
     * @throws MondeException Déclenche une exception si l'étape n'est pas mise en entrée
     */
    private void verifEntreePrede(EtapeIG e) throws MondeException{
        if(!e.estEntree() && e.getPredecesseurs().isEmpty())
                throw new MondeException("L'activité " + e.getNom()+ " n'a pas de predecesseurs donc doit être mise en Entrée", MondeException.TypeErreur.ERREUR_NON_ENTREE_NON_PRED);
    }

    /**
     * Méthode qui vérifie si une étape est bien mise en sortie si elle n'a aucun successeur
     * @param e L'étape à vérifier
     * @throws MondeException Déclenche une exception si l'étape n'est pas mise en sortie
     */
    private void verifSortieSucc(EtapeIG e) throws MondeException{
        if(!e.estSortie() && e.getSuccesseurs().isEmpty())
            throw new MondeException("L'activité " + e.getNom()+ " n'a pas de successeurs donc doit être mise en Sortie", MondeException.TypeErreur.ERREUR_NON_SORTIE_NON_SUCC);
    }


    /**
     * Méthode qui vérifie si une activité restreinte a été notée comme une entrée
     * @param e L'étape à vérifier
     * @throws MondeException Déclenche une exception si une activité restreinte est mise en entrée
     */
    private void verifActiviteRestreintePasEntree(EtapeIG e) throws MondeException {
        if(e.getSuccesseurs().getFirst().estActiviteRestreinte() && e.getSuccesseurs().getFirst().estEntree()) {
            throw new MondeException("L'activité " + e.getNom() + " est une activité restreinte et ne doit donc pas être une entrée !", MondeException.TypeErreur.ERREUR_ACTIVITE_RESTREINTE_ENTREE);
        }
    }

    /**
     * Méthode qui vérifie la validité d'un guichet
     * @param e Le guichet à vérifier
     * @throws MondeException Si le guichet n'est pas valide
     */
    private void verifierGuichet(EtapeIG e) throws MondeException {
        // Vérification du nombre de successeurs
        if (e.getSuccesseurs().size() != 1) {
            throw new MondeException("Le guichet " + e.getNom() + " doit avoir exactement un successeur",
                    MondeException.TypeErreur.NOMBRE_SUCCESSEURS_GUICHET);
        }

        // Vérification du type du successeur
        EtapeIG succ = e.premierSuccesseur(e);
        if (!succ.getType().equals("Activite")) {
            throw new MondeException("Le successeur du guichet " + e.getNom() + " doit être une activité",
                    MondeException.TypeErreur.SUCCESSEUR_GUICHET_INVALIDE);
        }

        // Identification de l'activité restreinte
        identifierActiviteRestreinte(e);
    }

    /**
     * Méthode qui crée un Monde selon les étapes d'un MondeIG
     * @return Le monde créé
     */
    private Monde creerMonde() {
        this.correspondance = new CorrespondancesEtapes();
        Monde monde = new Monde();

        for (EtapeIG e : this.mondeIG) {
            creationEtape(e, monde);
        }
        lierSuccesseurs();

        for (EtapeIG e : this.mondeIG) {
            ajouterEntreeSortie(e, monde);
        }

        return monde;
    }

    /**
     * Méthode qui identifie l'activité suivant le guichet comme une activité restreinte
     * @param e L'étape (guichet) dont le successeur doit être identifié comme activité restreinte
     */
    private void identifierActiviteRestreinte(EtapeIG e) {
        EtapeIG succ = e.premierSuccesseur(e);
        if (succ.getType().equals("Activite")) {
            ActiviteIG act = (ActiviteIG) succ;
            if (!act.estActiviteRestreinte()) {
                act.switchActiviteRestreinte();
            }
        }
    }

    /**
     * Méthode qui crée une étape dans le Monde à partir d'une étapeIG
     * @param e L'étapeIG à partir de laquelle créer une nouvelle étape dans le Monde
     * @param monde Le Monde dans lequel créer la nouvelle étape
     */
    private void creationEtape(EtapeIG e, Monde monde) {
        if (e.getType().equals("Activite")) {
            if (e.estActiviteRestreinte()) {
                creationActiviteRestreinte(e, monde);
            } else {
                creationActivite(e, monde);
            }
        } else {
            creationGuichet(e, monde);
        }
    }

    /**
     * Méthode qui crée une activité restreinte dans le Monde
     * @param e L'étape de la partie interface graphique à partir de laquelle on crée l'activité restreinte
     * @param monde Le Monde dans lequel on crée l'activité restreinte
     */
    private void creationActiviteRestreinte(EtapeIG e, Monde monde) {
        ActiviteRestreinte actRest = new ActiviteRestreinte(e.getNom(), e.getDelai(), e.getEcart());
        actRest.setLoi(e.getLois());
        monde.ajouter(actRest);
        this.correspondance.ajouter(e, actRest);
    }

    /**
     * Méthode qui crée une activité dans le Monde
     * @param e L'étape de la partie interface graphique à partir de laquelle on crée l'activité
     * @param monde Le Monde dans lequel on crée l'activité
     */
    private void creationActivite(EtapeIG e, Monde monde) {
        Activite act = new Activite(e.getNom(), e.getDelai(), e.getEcart());
        act.setLoi(e.getLois());
        monde.ajouter(act);
        this.correspondance.ajouter(e, act);
    }

    /**
     * Méthode qui crée un guichet dans le Monde
     * @param e L'étape de la partie interface graphique à partir de laquelle on crée le guichet
     * @param monde Le Monde dans lequel on crée le guichet
     */
    private void creationGuichet(EtapeIG e, Monde monde) {
        Guichet gui = new Guichet(e.getNom(), e.getNbJetons());
        monde.ajouter(gui);
        this.correspondance.ajouter(e, gui);
    }

    /**
     * Méthode qui fait le lien entre les successeurs du MondeIG et du Monde
     * @throws MondeException Si un successeur n'est pas trouvé
     */
    private void lierSuccesseurs() {
        for (EtapeIG e : this.mondeIG) {
            for (EtapeIG succ : e.getSuccesseurs()) {
                Etape etape = this.correspondance.get(e);
                Etape etapeSucc = this.correspondance.get(succ);

                if (etape == null || etapeSucc == null) {
                    throw new MondeException("Un successeur n'a pas été trouvé pour l'étape " + e.getNom(),
                            MondeException.TypeErreur.SUCCESSEUR_NON_TROUVE);
                }

                etape.ajouterSuccesseur(etapeSucc);
            }
        }
    }

    /**
     * Méthode qui ajoute les entrées et sorties au Monde
     * @param e L'étape dont on vérifie la qualité d'entrée ou de sortie
     * @param monde Le Monde dans lequel on ajoute les entrées / sorties
     */
    private void ajouterEntreeSortie(EtapeIG e, Monde monde) {
        if (e.estEntree()) {
            monde.aCommeEntree(this.correspondance.get(e));
            this.correspondance.ajouter(e, monde.getSasEntree());
        }
        if (e.estSortie()) {
            monde.aCommeSortie(this.correspondance.get(e));
            this.correspondance.ajouter(e, monde.getSasSortie());
        }
    }

    /**
     * Méthode qui lance la simulation du monde par introspection
     * @param monde Le monde sur lequel lancer la simulation
     */
    public void instrospectionSimu(Monde monde){
        this.nbClients = 6;

        try {
            clp = new ClassLoaderPerso(ClientTwisk.class.getClassLoader());

            Class<?> simulationClass = clp.loadClass("twisk.simulation.Simulation");

            Constructor<?> constructeur = simulationClass.getConstructor();
            instance = constructeur.newInstance();

            setNbClient = simulationClass.getMethod("setNbClients", int.class);
            setNbClient.invoke(instance, nbClients);

            Method ajouterObservateur = simulationClass.getMethod("ajouterObservateur", Observateur.class);
            ajouterObservateur.invoke(instance, this);

            Method setNomBibliotheque = simulationClass.getMethod("setNomBibliotheque", String.class);
            setNomBibliotheque.invoke(instance, "libTwisk");

            simuler = simulationClass.getMethod("simuler", Monde.class);
            simuler.invoke(instance, monde);

        } catch (ClassNotFoundException e) {
            System.err.println("La classe Simulation n'a pas été trouvée");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("La méthode demandée n'existe pas");
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Erreur lors de l'appel de la méthode");
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui permet la mise à jour graphique de la simulation
     */
    @Override
    public void reagir() {
        nettoyerClientsGuichets();  // Obligé pour les guichets car ils sont dans les stackpane de VueGuichetIG

        Task<Void> taskUpdateClient = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Object gestionnaire = getGestionnaire();
                    ArrayList<ClientIG> clientsIG = creerClientsIG(gestionnaire);
                    Platform.runLater(() -> SimulationIG.this.mondeIG.setClientIG(clientsIG));  // On exécute le code dans le thread dès que possible
                }
                catch(NoSuchMethodException | SecurityException e) {   // getMethod
                    System.err.println("Erreur lors du getMethod");
                    e.printStackTrace();
                }
                catch(IllegalAccessException | InvocationTargetException e) {
                    System.err.println("Erreur lors du invoke");
                }
                return null;
            }
        };
        ThreadsManager.getInstance().lancer(taskUpdateClient);  // On lance la tâche et on la met dans la liste des tâches en cours
        if(this.simuFinie()) {
            this.mondeIG.notifierObservateurs();
        }
    }

    /**
     * Méthode qui vérifie si la simulation est terminée
     * @return Vrai si la simulation est terminée, faux sinon
     */
    public boolean simuFinie() {
        if(instance == null) return false;  // Faux si elle n'est pas lancée
        try {
            Method m = instance.getClass().getMethod("estSimuFinie");
            return (boolean) m.invoke(instance);    // On utilise la fonction estFinie sur l'instance de la simulation
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Méthode qui récupère le gestionnaire de clients de Simulation par introspection
     * @return Le gestionnaire de clients de Simulation
     * @throws NoSuchMethodException Exception si la méthode getGestionnaireClients n'existe pas
     * @throws InvocationTargetException Exception relative à l'utilisation d'invoke lors du getGestionnaire
     * @throws IllegalAccessException Exception relative à une tentative d'accès à getGestionnaire
     */
    private Object getGestionnaire() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getGestionnaireClients = instance.getClass().getMethod("getGestionnaireClients");
        return getGestionnaireClients.invoke(instance);
    }

    /**
     * Méthode qui crée une liste de clientsIG
     * @param gestionnaire Le gestionnaire de clients
     * @return Une ArrayList de clientsIG
     * @throws NoSuchMethodException Exception si la méthode getClients, getNumero ou getEtape n'existe pas
     * @throws InvocationTargetException Exception relative à l'utilisation d'invoke
     * @throws IllegalAccessException Exception relative à une tentative d'accès à une méthode
     */
    private ArrayList<ClientIG> creerClientsIG(Object gestionnaire) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<ClientIG> clientsIG = new ArrayList<>();

        Method getClients = gestionnaire.getClass().getMethod("iterator");  // On récupère l'itérator pour l'appliquer sur le gestionnaire
        Iterator<?> it = (Iterator<?>) getClients.invoke(gestionnaire);

        int i = 0;  // Cpt pour décaler les clients lors de leur affichage
        while (it.hasNext()) {  // On itère à travers tous les clients
            Object client = it.next();

            // Numéro du client
            Method getNumero = client.getClass().getMethod("getNumeroClient");
            int numero = (int) getNumero.invoke(client);

            // Etape du client
            Method getEtape = client.getClass().getMethod("getEtape");
            Etape etape = (Etape) getEtape.invoke(client);

            // EtapeIG correspondante
            Method getNom = etape.getClass().getMethod("getNom");
            String nom = (String) getNom.invoke(etape);

            // On cherche une correspondance pour les SasEntree et SasSortie
            EtapeIG etapeIG = correspondanceSas(nom);

            // Activités, guichets et autres
            if(etapeIG == null) {
                etapeIG = trouverEtapeIG(nom);
            }

            // Erreur à modifier ?
            if (etapeIG == null) {
                System.err.println("Pas de correspondance pour " + etape.getNom());
                System.err.println("-> Classe exacte de l'étape : " + etape.getClass());
                continue;
            }

            // Coordonnées et couleur
            int couleur = creerCouleurClient(numero);

            // On ajoute le client à la liste des clients du guichet s'il est dans un guichet
            ClientIG clientIG = new ClientIG(numero, couleur, etapeIG);

            if(etapeIG.getType().equals("Guichet")) {
                GuichetIG guichetIG = (GuichetIG) etapeIG;
                guichetIG.ajouterClient(clientIG);
            }
            clientsIG.add(clientIG);

            i++;
            System.out.println(" -> Client " + numero + " dans " + etape.getNom());
        }

        return clientsIG;
    }

    /**
     * Méthode qui crée et stock une couleur pour un client
     * @param numero Le numéro du client
     * @return La couleur du client
     */
    private int creerCouleurClient(int numero) {
        if(!this.couleursClients.containsKey(numero)) {
            this.couleursClients.put(numero, new Random().nextInt(5));
        }
        return this.couleursClients.get(numero);
    }

    /**
     * Méthode qui cherche l'EtapeIG correspondant à un SasEntree ou un SasSortie
     * @param nom Le nom de l'Etape (Entree ou Sortie)
     * @return L'EtapeIG correspondante, sinon null
     */
    private EtapeIG correspondanceSas(String nom) {
        if (nom.equals("Entree") || nom.equals("Sortie")) {
            for (Integer id : this.correspondance.getHashmap().keySet()) {
                if (this.correspondance.getHashmap().get(id).getNom().equals(nom)) {
                    return this.correspondance.getHashmapIG().get(id);
                }
            }
        }
        return null;
    }

    /**
     * Méthode qui cherche l'EtapeIG correspondant à un nom parmi les étapes du MondeIG (Activités, Guichets, etc..)
     * @param nom Le nom de l'étape logique à rechercher
     * @return L'EtapeIG correspondante si trouvée, sinon null
     */
    private EtapeIG trouverEtapeIG(String nom) {
        for (EtapeIG e : mondeIG) {
            if (e.getNom().equals(nom)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Méthode qui rafraichit l'affichage des clients des guichets
     */
    private void nettoyerClientsGuichets() {
        for (EtapeIG e : this.mondeIG) {
            if (e.getType().equals("Guichet")) {
                GuichetIG gui = (GuichetIG) e;
                gui.viderClients();
            }
        }
    }
}