package twisk.sauvegarde;

/**
 * Classe EtapeIGDTO qui sert à conserver les données d'une EtapeIG (et ses sous-classes) pour sa conversion en JSON et sauvegarde
 */
public class EtapeDTO {
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
    public EtapeDTO(String nom, int identifiant, double posX, double posY, int largeur, int hauteur,
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

    /**
     * Méthode qui récupère le nom de l'étape DTO
     * @return Le nom de l'étape DTO
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Méthode qui récupère l'identifiant de l'étape DTO
     * @return L'identifiant de l'étape DTO
     */
    public int getIdentifiant() {
        return this.identifiant;
    }

    /**
     * Méthode qui récupère la pos x de l'étape DTO
     * @return La pos x de l'étape DTO
     */
    public double getPosX() {
        return this.posX;
    }

    /**
     * Méthode qui récupère la pos y de l'étape DTO
     * @return La pos y de l'étape DTO
     */
    public double getPosY() {
        return this.posY;
    }

    /**
     * Méthode qui récupère la largeur de l'étape DTO
     * @return La largeur de l'étape DTO
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Méthode qui récupère la hauteur de l'étape DTO
     * @return La hauteur de l'étape DTO
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Méthode qui dit si une étape DTO est une entrée
     * @return Vrai si l'étape est une entrée, faux sinon
     */
    public boolean estEntree() {
        return this.estEntree;
    }

    /**
     * Méthode qui dit si une étape DTO est une sortie
     * @return Vrai si l'étape est une sortie, faux sinon
     */
    public boolean estSortie() {
        return this.estSortie;
    }

    /**
     * Méthode qui dit donne le type de l'étape DTO (activité / guichet)
     * @return Le type de l'étape DTO
     */
    public String getType() {
        return this.type;
    }

    /**
     * Méthode qui récupère le délai de l'étape DTO
     * @return Le délai
     */
    public int getDelai() {
        return this.delai;
    }

    /**
     * Méthode qui récupère l'écart temps de l'étape DTO
     * @return L'écart temps
     */
    public int getEcart() {
        return this.ecart;
    }

    /**
     * Méthode qui récupère le nombre de jetons de l'étape DTO
     * @return Le nombre de jetons
     */
    public int getNbJetons() {
        return this.nbJetons;
    }
}