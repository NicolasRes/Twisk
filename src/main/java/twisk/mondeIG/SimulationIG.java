package twisk.mondeIG;

import twisk.monde.Monde;

/**
 * Classe qui gère la simulation de l'interface graphique
 */
public class SimulationIG {
    private MondeIG monde;

    /**
     * Constructeur de la classe SimulationIG
     * @param monde Le monde à simuler
     */
    public SimulationIG (MondeIG monde) {
        assert(monde != null);

        this.monde = monde;
    }

    public void simuler() {
        verifierMondeIG();
        //creerMonde();
    }

    /**
     * Méthode qui vérifie que les conditions du monde à simuler sont bien remplies
     */
    private void verifierMondeIG() {

        for(EtapeIG e : this.monde) {
            if(e.getType().equals("Guichet")) {
                guichetSansSuccesseur(e);
                succGuichetValide(e);
                guichetUnSuccesseur(e);
                guichetPasQueSortie(e);
                identifierActiviteRestreinte(e);

            }
            else {
                activiteVerifNbSuccPred(e);
            }
        }
    }

    /**
     * Méthode qui envoie une erreur si le guichet n'a pas de successeur
     */
    private void guichetSansSuccesseur(EtapeIG e) {
        if(e.getSuccesseurs() == null || e.getSuccesseurs().isEmpty()) {    // null = liste jamais initialisée et isEmpty initialisée mais ne contient aucun élément
            System.out.println("Erreur, le guichet " + e.getNom() + " n'a pas de successeur");
        }
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
     * Méthode qui envoie un messsage d'erreur si une étape a une entrée / une sortie sans avoir de prédecesseur / successeur
     * @param e L'étape à vérifier
     */
    private void activiteVerifNbSuccPred(EtapeIG e) {
        if(e.estEntree() && (e.getPredecesseurs().isEmpty())){
            System.out.println("Erreur, l'activité " + e.getNom() + " est une entrée, donc elle doit avoir au moins un prédécesseur");
        }

        if(e.estSortie() && (e.getSuccesseurs().isEmpty())){
            System.out.println("Erreur, l'activité " + e.getNom() + " est une sortie, donc elle doit avoir au moins un successeur");
        }
    }

    /**
     * Méthode qui envoie un messsage d'erreur si un guichet est bien une entrée et une sortie
     * @param e Le guichet à vérifier
     */
    private void guichetPasQueSortie(EtapeIG e) {
        if(!e.estSortie() || !e.estEntree()){
            System.out.println("Erreur, le guichet "+ e.getNom() + "doit être une Entrée / Sortie");
        }
    }

    /**
     * Méthode qui vérifie si un guichet a exactement un seul et unique successeur
     * @param e Le guichet à vérifier
     */
    public void guichetUnSuccesseur(EtapeIG e){
        if(e.getSuccesseurs().size()!=1){
            System.out.println("Erreur, le guichet " + e.getNom() + " a un nombre de successeurs incorrect, il doit avoir un seul successeur");
        }
    }

    /*private Monde creerMonde() {

    }*/
}