package twisk.mondeIG;

import twisk.monde.*;

/**
 * Classe qui gère la simulation de l'interface graphique
 */
public class SimulationIG {
    private MondeIG mondeIG;
    private CorrespondancesEtapes correspondance;

    /**
     * Constructeur de la classe SimulationIG
     * @param monde Le monde à simuler
     */
    public SimulationIG (MondeIG monde) {
        assert(monde != null);

        this.mondeIG = monde;
    }

    public void simuler() {
        verifierMondeIG();
        creerMonde();
        System.out.println("Fin vérif");
    }

    /**
     * Méthode qui vérifie que les conditions du monde à simuler sont bien remplies
     */
    private void verifierMondeIG() {
        for(EtapeIG e : this.mondeIG) {
            if(e.getType().equals("Guichet")) {
                if(guichetUnSuccesseur(e)) {
                    succGuichetValide(e);
                    identifierActiviteRestreinte(e);
                }
            }
        }
        mondeVide();
    }

    /**
     * Méthode qui renvoie une erreur si le successeur du guichet n'est pas une activité
     * @param e Le guichet dont on examine le type du successeur
     */
    private void succGuichetValide(EtapeIG e) {
        EtapeIG succ = e.premierSuccesseur(e);
        if(!succ.getType().equals("Activite")) {
            System.out.println("Erreur, le successeur du guichet " + e.getNom() + " doit forcément être une activité");
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
     * Méthode qui vérifie si un guichet a exactement un seul et unique successeur
     * @param e Le guichet à vérifier
     */
    public boolean guichetUnSuccesseur(EtapeIG e){
        if(e.getSuccesseurs().size()!=1){
            System.out.println("Erreur, le guichet " + e.getNom() + " a un nombre de successeurs incorrect, il doit avoir un seul successeur");
            return false;
        }
        return true;
    }

    public void mondeVide() {
        if(this.mondeIG.getEtapes().isEmpty()) {
            System.out.println("Erreur, le monde n'a aucune étape");
        }
    }

    private Monde creerMonde() {
        this.correspondance = new CorrespondancesEtapes();
        System.out.println(this.mondeIG.toString());
        Monde monde = new Monde();
        System.out.println("Création du monde");

        // On crée les étapes
        for(EtapeIG e : this.mondeIG) {
            System.out.println("Traitement " + e.getNom());
            if(e.getType().equals("Activite")) {
                if(e.estActiviteRestreinte()) {
                    ActiviteRestreinte actRest = new ActiviteRestreinte(e.getNom(), e.getDelai(), e.getEcart());
                    System.out.println("Activite restreinte créée " + actRest.getNom());
                    monde.ajouter(actRest);
                    this.correspondance.ajouter(e, actRest);
                    //this.correspondance.afficherHashmap(correspondance);
                }
                Activite act = new Activite(e.getNom(), e.getDelai(), e.getEcart());
                System.out.println("Activite créée " + act.getNom());
                monde.ajouter(act);
                this.correspondance.ajouter(e, act);
                //this.correspondance.afficherHashmap(correspondance);
            }
            else {
                Guichet gui = new Guichet(e.getNom(), e.getNbJetons());
                System.out.println("Guichet créé " + gui.getNom());
                monde.ajouter(gui);
                this.correspondance.ajouter(e, gui);
                //this.correspondance.afficherHashmap(correspondance);
            }


            // On ajoute les succ
            for (EtapeIG succ : e.getSuccesseurs()) {
                Etape etapeSucc = this.correspondance.get(succ);
                this.correspondance.getHashmap().get(etapeSucc.getNumero()).ajouterSuccesseur(etapeSucc);
            }

            // Gestion des entrées / sorties
            if(e.estEntree()) {
                monde.aCommeEntree(this.correspondance.get(e));
            }
            if(e.estSortie()) {
                monde.aCommeSortie(this.correspondance.get(e));
            }
        }
        this.correspondance.afficherHashmap(this.correspondance);

        //monde.toC();
        return monde;
    }
}