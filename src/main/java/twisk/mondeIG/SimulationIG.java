package twisk.mondeIG;

import twisk.monde.*;
import twisk.simulation.Simulation;

/**
 * Classe qui gère la simulation de l'interface graphique
 */
public class SimulationIG {
    private MondeIG mondeIG;
    private CorrespondancesEtapes correspondance;
    private Simulation simulation;

    /**
     * Constructeur de la classe SimulationIG
     * @param monde Le monde à simuler
     */
    public SimulationIG (MondeIG monde) {
        assert(monde != null);

        this.mondeIG = monde;
        this.simulation = new Simulation();
    }

    /**
     * Méthode qui lance une simulation à partir d'un Monde créé selon un MondeIG valide
     */
    public void simuler() {
        verifierMondeIG();
        Monde monde = creerMonde();
        this.simulation.setNbClients(6);  // Pour rendre la simulation fonctionnelle
        this.simulation.simuler(monde);
    }

    /**
     * Méthode qui vérifie que les conditions de validité du Monde à simuler sont bien remplies
     */
    private void verifierMondeIG() {
        boolean aEntree = false;
        boolean aSortie = false;

        for(EtapeIG e : this.mondeIG) {
            if(e.getType().equals("Guichet")) {
                if(guichetUnSuccesseur(e)) {
                    succGuichetValide(e);
                    identifierActiviteRestreinte(e);
                }
            }
            aEntree = aEntree(e);
            aSortie = aSortie(e);
        }
        if(!aEntree) {
            System.out.println("Erreur, le monde n'a aucune entrée");   // Remplacer par exception
        }
        if(!aSortie) {
            System.out.println("Erreur, le monde n'a aucune sortie");   // Remplacer par exception
        }
        mondeVide();
    }

    /**
     * Méthode qui crée un Monde selon les étapes d'un MondeIG
     * @return
     */
    private Monde creerMonde() {
        this.correspondance = new CorrespondancesEtapes();
        Monde monde = new Monde();

        // On crée les étapes
        for(EtapeIG e : this.mondeIG) {
            creationEtape(e, monde);
            lierSuccesseurs();
            ajouterEntreeSortie(e, monde);
        }

        // Pour le débogage
        //System.out.println(this.correspondance.toString());;

        return monde;
    }

    /**
     * Méthode qui vérifie une part de la validité du monde : renvoie une erreur si le successeur du guichet n'est pas une activité
     * @param e Le guichet dont on examine le type du successeur
     */
    private void succGuichetValide(EtapeIG e) {
        EtapeIG succ = e.premierSuccesseur(e);
        if(!succ.getType().equals("Activite")) {
            System.out.println("Erreur, le successeur du guichet " + e.getNom() + " doit forcément être une activité"); // Remplacer par exception
        }
    }

    /**
     * Méthode qui identifie l'activité suivant le guichet comme une activité restreinte
     * @param e L'étape (guichet) dont le successeur doit être identifié comme activité restreinte
     */
    private void identifierActiviteRestreinte(EtapeIG e) {
        EtapeIG succ = e.premierSuccesseur(e);   // On récupère le successeur du guichet (une activité)
        if(succ.getType().equals("Activite")) {
            ActiviteIG act = (ActiviteIG) succ;
            if(!act.estActiviteRestreinte()) {  // Si l'activité n'est pas déjà une activité restreinte, on la définie comme telle
                act.switchActiviteRestreinte();
            }
        }
    }

    /**
     * Méthode qui vérifie une part de la validité du monde :  si un guichet a exactement un seul et unique successeur
     * @param e Le guichet à vérifier
     */
    private boolean guichetUnSuccesseur(EtapeIG e){
        if(e.getSuccesseurs().size()!=1){
            System.out.println("Erreur, le guichet " + e.getNom() + " a un nombre de successeurs incorrect, il doit avoir un seul successeur"); // Remplacer par exception
            return false;
        }
        return true;
    }

    /**
     * Méthode qui vérifie une part de la validité du monde : si le MondeIG est vide
     */
    private void mondeVide() {
        if(this.mondeIG.getEtapes().isEmpty()) {    // Remplacer par exception
            System.out.println("Erreur, le monde n'a aucune étape");
        }
    }

    /**
     * Méthode qui vérifie si l'étape est une entrée du MondeIG
     * @param e L'étape à vérifier
     * @return Vrai si l'étape est une entrée du MondeIG, Faux sinon
     */
    private boolean aEntree(EtapeIG e) {
        return e.estEntree();
    }

    /**
     * Méthode qui vérifie si l'étape est une sortie du MondeIG
     * @param e L'étape à vérifier
     * @return Vrai si l'étape est une sortie du MondeIG, Faux sinon
     */
    private boolean aSortie(EtapeIG e) {
        return e.estSortie();
    }

    /**
     * Méthode qui crée une étape dans le Monde à partir d'une étapeIG
     * @param e L'étapeIG à partir de laquelle créer une nouvelle étape dans le Monde
     * @param monde Le Monde dans lequel créer la nouvelle étape
     */
    private void creationEtape(EtapeIG e, Monde monde) {
        if(e.getType().equals("Activite")) {
            if(e.estActiviteRestreinte()) {
                creationActiviteRestreinte(e, monde);
            }
            else {
                creationActivite(e, monde);
            }
        }
        else {
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
     */
    private void lierSuccesseurs() {
        for(EtapeIG e : this.mondeIG) {
            for (EtapeIG succ : e.getSuccesseurs()) {
                Etape etape = this.correspondance.get(e);
                Etape etapeSucc = this.correspondance.get(succ);

                if(etape != null && etapeSucc != null) {    // Remplacer par exception
                    etape.ajouterSuccesseur(etapeSucc);
                }
                else {
                    System.out.println("Erreur, successeur non trouvé");
                }
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
        if(e.estEntree()) {
            monde.aCommeEntree(this.correspondance.get(e));
        }
        if(e.estSortie()) {
            monde.aCommeSortie(this.correspondance.get(e));
        }
    }
}