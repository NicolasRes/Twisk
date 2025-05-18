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
    public int temps = 0;         // si activité
    public int ecartTemps = 0;    // pareil ici
    public int nbJetons = 0;      // si guichet
}