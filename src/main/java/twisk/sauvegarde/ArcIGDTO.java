package twisk.sauvegarde;

/**
 * Classe ArcIGDTO qui sert à conserver les données d'un ArcIG pour sa conversion en JSON et sauvegarde
 */
public class ArcIGDTO {
    public String identifiantSource;
    public String identifiantDestination;

    /**
     * Constructeur de la classe ArcIGDTO
     * @param idSource L'identifiant du PDC source
     * @param idDest L'identifiant du PDC destination
     */
    public ArcIGDTO(String idSource, String idDest) {
        this.identifiantSource = idSource;
        this.identifiantDestination = idDest;
    }

    /**
     * Méthode qui renvoie l'identifiant du PDC source
     * @return L'identifiant du PDC source
     */
    public String getIdSource() {
        return this.identifiantSource;
    }

    /**
     * Méthode qui renvoie l'identifiant du PDC destination
     * @return L'identifiant du PDC destination
     */
    public String getIdDestination() {
        return this.identifiantDestination;
    }
}
