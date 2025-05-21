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
}
