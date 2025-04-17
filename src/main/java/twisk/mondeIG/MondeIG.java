package twisk.mondeIG;

import twisk.exceptions.TwiskArcException;
import twisk.exceptions.TwiskMenuException;
import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe MondeIG qui gère une collection d'EtapeIG
 */
public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {
    private HashMap<String, EtapeIG> etapes;
    private ArrayList<ArcIG> arcs;
    private ArrayList<PointDeControleIG> pointsSelectionnes;
    private ArrayList<EtapeIG> etapesSelectionnees;
    private ArrayList<ArcIG> arcsSelectionnes;

    /**
     * Constructeur de la classe MondeIG
     */
    public MondeIG() {
        this.etapes = new HashMap<>();
        ajouter("Activité");    // À sa construction, le monde contient une activité

        this.arcs = new ArrayList<>();
        this.pointsSelectionnes = new ArrayList<>();
        this.etapesSelectionnees = new ArrayList<>();
        this.arcsSelectionnes = new ArrayList<>();
    }

    /**
     * Méthode qui ajoute une étape au monde
     * @param type Le type d'étape à ajouter au monde
     */
    public void ajouter(String type) {
        assert(type != null);

        if(type.equals("Activité")) {
            EtapeIG act = new ActiviteIG("Act", TailleComposants.LARGEUR_ETAPE, TailleComposants.HAUTEUR_ETAPE);
            this.etapes.put(act.getIdentifiant(), act);
            notifierObservateurs();
        }
    }

    /**
     * Méthode qui permet d'itérer à travers les étapes du monde
     * @return Un itérateur d'EtapeIG
     */
    public Iterator<EtapeIG> iterator() {
        return this.etapes.values().iterator();
    }

    /**
     * Méthode qui permet de parcourir les arcs du monde
     * @return Un objet ArcIG
     */
    public Iterator<ArcIG> iteratorArc() {
        return this.arcs.iterator();
    }

    /**
     * Méthode qui permet d'itérer sur les arcs du monde
     * @return Un objet itérable
     */
    public Iterable<ArcIG> getArcsIterable() {
        return this.arcs;
    }

    /**
     * Méthode qui renvoie la HashMap contenant les étapes du monde
     * @return La HashMap contenant les étapes du monde
     */
    public HashMap<String, EtapeIG> getEtapes() {
        return this.etapes;
    }

    /**
     * Méthode qui affiche une erreur si deux points font partis de la même étape
     * @param pt1 La source de l'arc
     * @param pt2 La destination de l'arc
     * @return Vrai si les points appartiennent à la même étape, faux sinon
     */
    public boolean pointsMemeEtape(PointDeControleIG pt1, PointDeControleIG pt2) {
        return pt1.getEtape().getIdentifiant().equals(pt2.getEtape().getIdentifiant());
    }

    /**
     * Méthode qui affiche une erreur si on tente de créer un arc qui existe déjà
     * @param pt1 La source de l'arc
     * @param pt2 La destination de l'arc
     * @return Vrai si l'arc existe déjà, faux sinon
     */
    public boolean arcExistant(PointDeControleIG pt1, PointDeControleIG pt2) {
        for (ArcIG arc : this.arcs) {
            if(arc.getSource().equals(pt1) && arc.getDestination().equals(pt2)) {
                return true;
            }
            if(arc.getSource().equals(pt2) && arc.getDestination().equals(pt1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui vérifie la validité d'un arc
     * @param pt1 La source de l'arc
     * @param pt2 La destination de l'arc
     */
    public void arcValide(PointDeControleIG pt1, PointDeControleIG pt2) throws TwiskArcException {
        if(pointsMemeEtape(pt1, pt2)) {
            this.pointsSelectionnes.clear();
            throw new TwiskArcException("Erreur : les points appartiennent à la même étape");
        }
         if(arcExistant(pt1, pt2)) {
             this.pointsSelectionnes.clear();
             throw new TwiskArcException("Erreur : l'arc existe déjà");
         }
    }

    /**
     * Méthode qui ajoute un nouvel arc à la liste des arcs du monde
     * @param pt1 Point de contrôle de départ de l'arc
     * @param pt2 Point de contrôle d'arrivée de l'arc
     */
    public void ajouter(PointDeControleIG pt1, PointDeControleIG pt2) throws TwiskArcException {
        assert(pt1 != null);
        assert(pt2 != null);
        arcValide(pt1, pt2);

        ArcIG arc = new ArcIG(pt1, pt2);
        this.arcs.add(arc);
        notifierObservateurs();
    }

    /**
     * Méthode qui vérifie si un point est déjà sélectionné
     * @param pt1 Le point à vérifier
     * @return Vrai si le point est déjà sélectionné, faux sinon
     */
    public boolean estSelectionne(PointDeControleIG pt1) {
        for (PointDeControleIG p : this.pointsSelectionnes) {
            if (p.equals(pt1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui crée un arc lorsque deux points de contrôle sont sélectionnés
     * @param pt1 Le pt1 de contrôle à ajouter à l'ArrayList
     */
    public void pointsSelectionne(PointDeControleIG pt1) throws TwiskArcException {
        if(estSelectionne(pt1)) {   // Si le point est déjà dans la liste, on le retire
            this.pointsSelectionnes.remove(pt1);
        }
        else {  // Sinon on l'ajoute
            this.pointsSelectionnes.add(pt1);
        }

        if(this.pointsSelectionnes.size() == 2) {
            ajouter(this.pointsSelectionnes.get(0), this.pointsSelectionnes.get(1));    // On crée l'arc
            this.pointsSelectionnes.clear();
        }

        notifierObservateurs();
    }

    /**
     * Méthode pour notifier le monde lorsqu'une étape est sélectionnée ou désélectionnée
     * @param etape L'étape dont l'état a changé
     */
    public void etapesSelection(EtapeIG etape) {
        assert(etape != null);

        if(etape.estSelection()) {
            if(!this.etapesSelectionnees.contains(etape)) {
                this.etapesSelectionnees.add(etape);
            }
        }
        else {
            this.etapesSelectionnees.remove(etape);
        }
        notifierObservateurs();
    }

    /**
     * Méthode qui supprime les arcs associés aux étapes sélectionnées
     * @param etape Une étape sélectionnée
     */
    public void supprimerArcsAssocies(EtapeIG etape) {
        Iterator<ArcIG> iArc = this.arcs.iterator();
        while(iArc.hasNext()) {
            ArcIG arc = iArc.next();
            if(arc.getSource().getEtape().equals(etape) || arc.getDestination().getEtape().equals(etape)) {
                iArc.remove();
            }
        }
    }

    /**
     * Méthode qui supprime les étapes et les arcs associés
     */
    public void supprimerEtapesArcs() {
        for(EtapeIG etape : this.etapesSelectionnees) {
            supprimerArcsAssocies(etape);
            this.etapes.remove(etape.getIdentifiant());
        }

        this.pointsSelectionnes.clear();
        this.etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Méthode qui permet de renommer l'étape sélectionnée à partir du menu
     * @param nom Le nouveau nom entré par l'utilisateur
     */
    public void renommerEtape(String nom) throws TwiskMenuException {
            EtapeIG etape = this.etapesSelectionnees.getFirst();
            etape.setNom(nom);
            notifierObservateurs();
    }

    /**
     * Méthode qui calcule les nouvelles coordonnées d'une étape lors d'un drag'n drop
     * @param idEtape L'étape à déplacer
     * @param x La coordonnée x de l'étape
     * @param y La coordonnée y de l'étape
     */
    public void deplacerEtape(String idEtape, double x, double y) {
        assert(idEtape != null);
        assert(x >= 0);
        assert(y >= 0);

        EtapeIG etape = this.etapes.get(idEtape);   // On récupère l'étape qu'on déplace
            double deplacementX = x - etape.getPosX();
            double deplacementY = y - etape.getPosY();

            etape.setPosX(x); // On change les coordonnées de l'étape
            etape.setPosY(y);

            for(PointDeControleIG p : etape) {  // On change les coordonnéees des points associés
                p.deplacer(deplacementX, deplacementY);
            }

            notifierObservateurs();
    }

    /**
     * Méthode pour notifier le monde lorsqu'un arc est sélectionnée ou désélectionnée
     * @param arc L'arc dont l'état a changé
     */
    public void arcsSelection(ArcIG arc) {
        assert(arc != null);

        if(arc.estSelection()) {
            if(!this.arcsSelectionnes.contains(arc)) {
                this.arcsSelectionnes.add(arc);
            }
        }
        else {
            this.arcsSelectionnes.remove(arc);
        }
        notifierObservateurs();
    }

    /**
     * Méthode qui supprime les étapes et les arcs sélectionnés
     */
    public void supprimerSelection() {
        for(EtapeIG etape : this.etapesSelectionnees) {
            this.etapes.remove(etape.getIdentifiant());
        }
        for(ArcIG arc : this.arcsSelectionnes) {
            this.arcs.remove(arc);
        }

        this.pointsSelectionnes.clear();
        this.etapesSelectionnees.clear();
        this.arcsSelectionnes.clear();

        notifierObservateurs();
    }

    /**
     * Méthode qui désélectionne les étapes sélectionnées
     */
    public void deselectionnerEtapes() {
        for (EtapeIG etape : this.etapesSelectionnees) {
            if (etape.estSelection()) {
                etape.switchSelection();
            }
        }
        this.etapesSelectionnees.clear();
    }

    /**
     * Méthode qui désélectionne les arcs sélectionnées
     */
    public void deselectionnerArcs() {
        for (ArcIG arc : this.arcsSelectionnes) {
            if (arc.estSelection()) {
                arc.switchSelection();
            }
        }
        this.arcsSelectionnes.clear();
    }

    /**
     * Méthode qui permet de désélectionner tous les élements sélectionnés
     */
    public void deselectionnerTout() {
        deselectionnerEtapes();
        deselectionnerArcs();
        this.pointsSelectionnes.clear();    // Pas besoin de switch sur les points donc on se contente de vider l'ArrayList

        notifierObservateurs();
    }

    /**
     * Méthode qui permet de définir les activités sélectionnées comme les entrées du monde
     */
    public void choisirEntrees() {
        for(EtapeIG etape : this.etapesSelectionnees) {
            etape.switchEntree();
        }

        deselectionnerTout();
    }

    /**
     * Méthode qui permet de définir les activités sélectionnées comme les sorties du monde
     */
    public void choisirSorties() {
        for(EtapeIG etape : this.etapesSelectionnees) {
            etape.switchSortie();
        }

        deselectionnerTout();
    }

    /**
     * Méthode qui vérifie si une seule étape est bien sélectionnée
     * @return Vrai si une étape est sélectionnée, Faux sinon
     */
    public boolean uneEtapeSelectionnee() {
        return this.etapesSelectionnees.size() == 1;
    }

    /**
     * Méthode qui définit le délai d'une activité
     * @param delai Le délai de l'activité
     * @throws TwiskMenuException Exception déclenchée si l'étape sélectionnée n'est pas une activité
     */
    public void definirDelai(int delai) throws TwiskMenuException {
        if(this.etapesSelectionnees.get(0).getType().equals("Activite")) {   // Si c'est bien une activité
            ActiviteIG act = (ActiviteIG) this.etapesSelectionnees.get(0);
            act.setDelai(delai);
            notifierObservateurs();
        }
        else {
            throw new TwiskMenuException("Erreur, l'étape sélectionnée n'est pas une activité");
        }
    }

    /**
     * Méthode qui définit le délai d'une activité
     * @param ecart L'écart de l'activité
     * @throws TwiskMenuException Exception déclenchée si l'étape sélectionnée n'est pas une activité
     */
    public void definirEcart(int ecart) throws TwiskMenuException {
        if(this.etapesSelectionnees.get(0).getType().equals("Activite")) {   // Si c'est bien une activité
            ActiviteIG act = (ActiviteIG) this.etapesSelectionnees.get(0);
            act.setEcart(ecart);
            notifierObservateurs();
        }
        else {
            throw new TwiskMenuException("Erreur, l'étape sélectionnée n'est pas une activité");
        }
    }

    /**
     * Méthode qui renvoie une version String d'un MondeIG
     * @return Les informations d'un MondeIG sous forme de String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MondeIG \n");

        sb.append("=== Étapes === \n[");
        Iterator<EtapeIG> itEtapes = this.iterator();
        while(itEtapes.hasNext()) {
            sb.append(itEtapes.next().toString());
        }
        sb.append("]\n");

        sb.append("Arcs : \n");
        Iterator<ArcIG> itArcs = this.iteratorArc();
        while(itArcs.hasNext()) {
            sb.append(itArcs.next().toString());
            if(itArcs.hasNext()) {
                sb.append(",\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}