package twisk.mondeIG;

import twisk.ClientTwisk;
import twisk.exceptions.MondeException;
import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.FabriqueNumero;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe qui gère la simulation de l'interface graphique
 */
public class SimulationIG {
    private final MondeIG mondeIG;
    private CorrespondancesEtapes correspondance;
    private int nbClients;

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
    }

    /**
     * Méthode qui lance une simulation à partir d'un Monde créé selon un MondeIG valide
     */
    public void simuler() {
        verifierMondeIG();
        Monde monde = creerMonde();
        instrospectionSimu(monde);
        System.gc();
    }

    /**
     * Méthode qui vérifie que les conditions de validité du Monde à simuler sont bien remplies
     * @throws MondeException Si le monde n'est pas valide
     */
    private void verifierMondeIG() throws MondeException {
        boolean aEntree = false;
        boolean aSortie = false;

        if (this.mondeIG.getEtapes().isEmpty()) {
            throw new MondeException("Le monde n'a aucune étape", MondeException.TypeErreur.MONDE_VIDE);
        }

        for (EtapeIG e : this.mondeIG) {
            if (e.getType().equals("Guichet")) {
                verifierGuichet(e);
            }
            if (e.estEntree()) {
                aEntree = true;
            }
            if (e.estSortie()) {
                aSortie = true;
            }
            verifEntreePrede(e);
            verifSortieSucc(e);
            verifSortiePasSucc(e);
        }

        if (!aEntree) {
            throw new MondeException("Aucune entrée n'a été définie dans le monde", MondeException.TypeErreur.AUCUNE_ENTREE);
        }
        if (!aSortie) {
            throw new MondeException("Aucune sortie n'a été définie dans le monde", MondeException.TypeErreur.AUCUNE_SORTIE);
        }
    }

    private void verifEntreePrede(EtapeIG e) throws MondeException{
        if(!e.estEntree() && e.getPredecesseurs().isEmpty())
                throw new MondeException("L'activité " + e.getNom()+ " n'a pas de predecesseurs donc doit être mise en Entrée", MondeException.TypeErreur.ERREUR_NON_ENTREE_NON_PRED);
    }

    private void verifSortieSucc(EtapeIG e) throws MondeException{
        if(!e.estSortie() && e.getSuccesseurs().isEmpty())
            throw new MondeException("L'activité " + e.getNom()+ " n'a pas de successeurs donc doit être mise en Sortie", MondeException.TypeErreur.ERREUR_NON_SORTIE_NON_SUCC);
    }

    private void verifSortiePasSucc(EtapeIG e) throws MondeException{
        if(e.estSortie()&& !e.getSuccesseurs().isEmpty()){
            throw new MondeException("L'activité " + e.getNom()+ " est mise en sortie mais a un ou plusieurs successeurs !", MondeException.TypeErreur.ERREUR_SORTIE_NON_VIDE);
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
        // Gestion des entrées / sorties
        if (e.estEntree()) {
            monde.aCommeEntree(this.correspondance.get(e));
        }
        if (e.estSortie()) {
            monde.aCommeSortie(this.correspondance.get(e));
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
}