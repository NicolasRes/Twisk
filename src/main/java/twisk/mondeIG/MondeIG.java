package twisk.mondeIG;

import twisk.exceptions.TwiskArcException;
import twisk.exceptions.TwiskJetonsException;
import twisk.exceptions.TwiskMenuException;
import twisk.outils.TailleComposants;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Classe MondeIG qui gère une collection d'EtapeIG
 */
public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {
    private HashMap<String, EtapeIG> etapes;
    private ArrayList<EtapeIG> etapesSelectionnees;
    private ArrayList<ArcIG> arcs;
    private ArrayList<ArcIG> arcsSelectionnes;
    private ArrayList<PointDeControleIG> pointsSelectionnes;
    private ArrayList<ClientIG> clientsIG;

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
        this.clientsIG = new ArrayList<>();
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
        if(type.equals("Guichet")) {
            EtapeIG gui = new GuichetIG("Gui", TailleComposants.LARGEUR_GUICHET, TailleComposants.HAUTEUR_GUICHET);
            this.etapes.put(gui.getIdentifiant(), gui);
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
     * Méthode qui renvoie la liste des arcs du monde
     * @return Mes arcs du monde
     */
    public ArrayList<ArcIG> getArcs() {return this.arcs;}

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

        EtapeIG source = pt1.getEtape();
        EtapeIG destination = pt2.getEtape();

        if (creeraitCycle(source, destination)) {
            throw new TwiskArcException("Ajout d’un arc créerait un cycle entre " + source.getNom() + " et " + destination.getNom());
        }


        ArcIG arc = new ArcIG(pt1, pt2);

        // Gestion successeur / prédécesseur en cas d'ajout d'un arc
        if(!pt1.getEtape().getSuccesseurs().contains(pt2.getEtape())) {
            pt1.getEtape().ajouterSuccesseur(pt2.getEtape());
            pt2.getEtape().ajouterPredecesseur(pt1.getEtape());
        }

        definirSensGuichet(arc, arc.getSource(), arc.getDestination());

        this.arcs.add(arc);
        notifierObservateurs();
    }


    /**
     * Méthode qui définit ou vérifie le sens de circulation d’un guichet lorsqu’un arc est ajouté
     * @param arc L'arc qu'on lie au guichet
     * @param src Le point de contrôle source de l'arc
     * @param dst Le point de contrôle destination de l'arc
     * @throws TwiskArcException si l'arc n'est pas cohérent avec le sens déjà fixé du guichet
     */
    private void definirSensGuichet(ArcIG arc, PointDeControleIG src, PointDeControleIG dst) throws TwiskArcException {
        EtapeIG eSrc = src.getEtape();
        EtapeIG eDst = dst.getEtape();

        // Si aucune étape liée à l'arc n'est un guichet, on ne fait rien
        if(!eSrc.getType().equals("Guichet") && !eDst.getType().equals("Guichet")) return;

        // On récupère le guichet et ses points de contrôle
        GuichetIG guichet = eSrc.getType().equals("Guichet") ? (GuichetIG) eSrc : (GuichetIG) eDst;
        PointDeControleIG pdcG = guichet.getPointGauche();
        PointDeControleIG pdcD = guichet.getPointDroit();
        GuichetIG.Sens sensDeduit = deduireSens(arc, pdcG, pdcD);

        // Si pas encore de sens de défini, on le change
        if (guichet.getSens() == GuichetIG.Sens.AUCUN) {
            guichet.setSensGuichet(sensDeduit);
        }
        if (sensInterdit(arc, guichet, pdcG, pdcD)) {
            this.pointsSelectionnes.clear();
            notifierObservateurs();
            throw new TwiskArcException("Erreur : l’arc est incohérent avec le sens du guichet " + guichet.getNom());
        }
    }

    /**
     * Méthode qui déduit le sens de circulation à partir de la direction de l’arc et de la position du point
     * @param arc L'arc qu'on lie au guichet
     * @param pdcG Le PDC gauche du guichet
     * @param pdcD Le PDC droit du guichet
     * @return Le sens déduit
     */
    private GuichetIG.Sens deduireSens(ArcIG arc, PointDeControleIG pdcG, PointDeControleIG pdcD) {
        if (arc.estEntrantDans(pdcG)) {
            return GuichetIG.Sens.GAUCHE_DROITE;
        }
        else if (arc.estEntrantDans(pdcD)) {
            return GuichetIG.Sens.DROITE_GAUCHE;
        }
        else if (arc.estSortantDe(pdcG)) {
            return GuichetIG.Sens.DROITE_GAUCHE;
        }
        else if (arc.estSortantDe(pdcD)) {
            return GuichetIG.Sens.GAUCHE_DROITE;
        }
        else {
            throw new IllegalArgumentException("Arc non relié au guichet");
        }
    }

    /**
     * Méthode qui renvoie si le sens de création d'un arc est interdit ou non
     * @param arc L'arc qu'on vérifie
     * @param guichet Le guichet
     * @param pdcG Le PDC gauche du guichet
     * @param pdcD Le PDC droit du guichet
     * @return Vrai si sens interdit, faux si autorisé
     */
    private boolean sensInterdit(ArcIG arc, GuichetIG guichet, PointDeControleIG pdcG, PointDeControleIG pdcD) {
        switch (guichet.getSens()) {
            case GAUCHE_DROITE:
                return arc.estSortantDe(pdcG) || arc.estEntrantDans(pdcD);

            case DROITE_GAUCHE:
                return arc.estSortantDe(pdcD) || arc.estEntrantDans(pdcG);

            default:
                return false; // AUCUN : on autorise tout
        }
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
                arc.getSource().getEtape().getSuccesseurs().remove(arc.getDestination().getEtape());
                arc.getDestination().getEtape().getPredecesseurs().remove(arc.getSource().getEtape());
                iArc.remove();
            }
        }
    }

    /**
     * Méthode qui supprime les étapes et les arcs associés
     */
    public void supprimerEtapesArcs() {
        // Suppression étapes sélectionnées
        for(EtapeIG etape : this.etapesSelectionnees) {
            supprimerArcsAssocies(etape);
            this.etapes.remove(etape.getIdentifiant());
        }

        // Suppression arcs sélectionnés
        for (ArcIG arc : this.arcsSelectionnes) {
            EtapeIG src = arc.getSource().getEtape();
            EtapeIG dst = arc.getDestination().getEtape();

            src.getSuccesseurs().remove(dst);
            dst.getPredecesseurs().remove(src);
            this.arcs.remove(arc);

            // Vérifie pour les deux extrémités si ce sont des guichets
            if (src.getType().equals("Guichet")) {
                reinitialiserSensSiPlusDArcs((GuichetIG) src);
            }
            if (dst.getType().equals("Guichet")) {
                reinitialiserSensSiPlusDArcs((GuichetIG) dst);
            }
        }

        this.pointsSelectionnes.clear();
        this.etapesSelectionnees.clear();
        this.arcsSelectionnes.clear();

        notifierObservateurs();
    }

    /**
     * Méthode qui permet de réinitialiser le sens d'un guichet s'il n'a pas d'arc associé à ses deux PDC
     * @param guichet Le guichet dont on réinitialise le sens
     */
    private void reinitialiserSensSiPlusDArcs(GuichetIG guichet) {
        for (ArcIG arc : arcs) {
            if (arc.getSource().getEtape() == guichet || arc.getDestination().getEtape() == guichet) {
                return; // au moins un arc encore lié : on ne fait rien
            }
        }
        guichet.setSensGuichet(GuichetIG.Sens.AUCUN);
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
     * Méthode qui définit le nombre de jetons d'un guichet
     * @param jetons Le nombre de jetons à associer au guichet
     * @throws TwiskJetonsException Exception déclenchée si le nombre de jetons attribué au guichet est incorrect
     */
    public void definirJetons(int jetons) throws TwiskJetonsException {
        if(this.etapesSelectionnees.getFirst().getType().equals("Guichet")) {
            GuichetIG gui = (GuichetIG) this.etapesSelectionnees.getFirst();
            gui.setNbJetons(jetons);
            notifierObservateurs();
        }
        else {
            throw new TwiskJetonsException("Erreur, l'étape sélectionnée n'est pas un guichet");
        }
    }

    /**
     * Méthode qui set les clients dans une ArrayList et notifie les observateurs
     * @param clientsIG La liste des clients à set dans l'ArrayList du MondeIG
     */
    public void setClientIG(ArrayList<ClientIG> clientsIG) {
        this.clientsIG = clientsIG;
        notifierObservateurs();
    }

    /**
     * Méthode qui renvoie la liste des clientsIG
     * @return La liste des clientsIG
     */
    public ArrayList<ClientIG> getClientsIG() {
        return this.clientsIG;
    }

    /**
     * Méthode qui renvoie une version String d'un MondeIG
     * @return Les informations d'un MondeIG sous forme de String
     */
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

    /**
     * Méthode qui détermine si l'ajout d'un arc de `source` vers `destination` créerait un cycle.
     * Un cycle se forme s'il existe déjà un chemin de `destination` vers `source`, donc ajouter l'arc
     * reviendrait à boucler sur soi-même.
     *
     * @param source L'étape source du nouvel arc.
     * @param destination L'étape destination du nouvel arc.
     * @return true si l'ajout de l'arc créerait un cycle, false sinon.
     */
    private boolean creeraitCycle(EtapeIG source, EtapeIG destination) {

        return existeChemin(destination, source, new HashSet<>()); // existe déjà un chemin?
    }

    /**
     * Méthode récursive qui vérifie s'il existe un chemin d'une étape `courant` jusqu'à l'étape `cible`.
     * Utilise une recherche en profondeur (DFS) avec un ensemble d'étapes déjà visitées pour éviter les boucles infinies.
     *
     * @param courant L'étape courante depuis laquelle on cherche à atteindre la cible.
     * @param cible L'étape qu'on cherche à atteindre.
     * @param visites Ensemble des étapes déjà visitées pour ne pas repasser plusieurs fois au même endroit.
     * @return true s'il existe un chemin de `courant` vers `cible`, false sinon.
     */
    private boolean existeChemin(EtapeIG courant, EtapeIG cible, Set<EtapeIG> visites) {

        if (courant == cible) return true;
        visites.add(courant);

        for (EtapeIG successeur : courant.getSuccesseurs()) {
            if (!visites.contains(successeur)) {
                if (existeChemin(successeur, cible, visites)) {
                    return true;
                }
            }
        }
        return false; // Aucun chemin trouvé, pas cycle
    }

    /**
     * Méthode qui signale la fin de la simulation
     */
    public void simulationTerminee() {
        notifierObservateurs();
    }
}