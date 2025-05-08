package twisk.mondeIG;

import twisk.monde.Etape;

import java.util.HashMap;

/**
 * Classe CorrespondancesEtapes qui gère la correspondance entre les étapes de l'interface graphique et du projet Twisk
 */
public class CorrespondancesEtapes {
    private HashMap<Integer, EtapeIG> etapesIG;
    private HashMap<Integer, Etape> etapes;

    /**
     * Constructeur de la classe CorrespondancesEtapes
     */
    public CorrespondancesEtapes() {
        this.etapesIG = new HashMap<>();
        this.etapes = new HashMap<>();
    }

    /**
     * Méthode qui ajoute les étapes du Monde et du MondeIG dans leurs listes respectives
     * @param etapeIG Les étapes du MondeIG
     * @param etape Les étapes du Monde
     */
    public void ajouter(EtapeIG etapeIG, Etape etape) {
        int identifiant = etapeIG.getIdentifiantEtape();
        this.etapesIG.put(identifiant, etapeIG);
        this.etapes.put(identifiant, etape);
    }

    /**
     * Méthode qui récupère une étape du Monde à partir de l'identifiant de son étape associée dans le MondeIG
     * @param etapeIG L'étape du MondeIG dont on veut récupérer le correspondant
     * @return L'étape correspondante à l'étape du MondeIG passée en paramètre
     */
    public Etape get(EtapeIG etapeIG) {
        return this.etapes.get(etapeIG.getIdentifiantEtape());
    }

    /**
     * Méthode qui récupère une étape du MondeIG à partir de l'identifiant de son étape associée dans le Monde
     * @param etape L'étape du Monde dont on veut récupérer le correspondant
     * @return L'EtapeIG correspondante à l'étape du Monde passée en paramètre
     */
    public EtapeIG get(Etape etape) {
        for(Integer id : this.etapes.keySet()) {
            if(this.etapes.get(id).equals(etape)) {
                return this.etapesIG.get(id);
            }
        }
        return null;
    }

    /**
     * Méthode qui renvoie la HashMap des étapes du MondeIG
     * @return La liste des étapes du MondeIG
     */
    public HashMap<Integer, EtapeIG> getHashmapIG() {
        return this.etapesIG;
    }

    /**
     * Méthode qui renvoie la Hashmap des étapes du Monde
     * @return La liste des étapes du Monde
     */
    public HashMap<Integer, Etape> getHashmap() {
        return this.etapes;
    }

    /**
     * Méthode qui renvoie une version String de la correspondance des étapes du Monde et du MondeIG
     * @return Les informations des étapes du Monde et du MondeIG ainsi que leurs liens
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Integer id : etapesIG.keySet()) {
            sb.append("Identifiant: ").append(id).append("\n");
            sb.append("   EtapeIG: ").append(etapesIG.get(id)).append("\n");
            sb.append("   Etape  : ").append(etapes.get(id)).append("\n");
        }

        sb.append("\n");

        for (Integer id : this.getHashmapIG().keySet()) {
            sb.append("IG ").append(id).append(" -> ").append(this.getHashmapIG().get(id)).append("\n");
        }
        for (Integer id : this.getHashmap().keySet()) {
            sb.append("Monde ").append(id).append(" -> ").append(this.getHashmap().get(id)).append("\n");
        }

        return sb.toString();
    }
}
