package twisk.sauvegarde;

/**
 * Classe EtapeIGDTO qui sert à conserver les données d'une EtapeIG (et ses sous-classes) pour sa conversion en JSON et sauvegarde
 */
public class EtapeIGDTO {
    public String nom;
    public int identifiant;
    public double posX;
    public double posY;
    public int largeur;
    public int hauteur;
    public boolean estEntree;
    public boolean estSortie;
    public String type;
    public int delai = 0;         // si activité
    public int ecart = 0;         // pareil ici
    public int nbJetons = 0;      // si guichet

    /**
     * Constructeur de la classe EtapeIGDTO
     * @param nom Le nom
     * @param identifiant L'identifiant unique
     * @param posX La position x
     * @param posY La position y
     * @param largeur La largeur
     * @param hauteur La hauteur
     * @param estEntree Si l'étape est une entrée ou non
     * @param estSortie Si l'étape est une sortie ou non
     * @param type Activité ou guichet
     */
    public EtapeIGDTO(String nom, int identifiant, double posX, double posY, int largeur, int hauteur,
                      boolean estEntree, boolean estSortie, String type) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.posX = posX;
        this.posY = posY;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.estEntree = estEntree;
        this.estSortie = estSortie;
        this.type = type;
    }

    /**
     * Méthode qui initialise le delai avec la valeur entrée (uniquement si l'étape est une activité)
     * @param delai La valeur du délai
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Méthode qui initialise l'écart avec la valeur entrée (uniquement si l'étape est une activité)
     * @param ecart La valeur de l'écart
     */
    public void setEcart(int ecart) {
        this.ecart = ecart;
    }

    /**
     * Méthode qui initialise le nombre de jetons avec la valeur entrée (uniquement si l'étape est un guichet)
     * @param nbJetons Le nombre de jetons
     */
    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }
}