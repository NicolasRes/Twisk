package twisk.sauvegarde;

/**
 * Classe qui gère les points de contrôle DTO
 */
public class PointDeControleDTO {
    public String identifiant;
    public double relativeX;
    public double relativeY;

    /**
     * Constructeur de PointsDeControleDTO
     * @param identifiant L'identifiant du PDC
     * @param relativeX La position relative X du PDC
     * @param relativeY La position relative X du PDC
     */
    public PointDeControleDTO(String identifiant, double relativeX, double relativeY) {
        this.identifiant = identifiant;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    /**
     * Méthode qui récupère
     * @return
     */
    public String getIdentifiant() {
        return this.identifiant;
    }

    public double getRelativeX() {
        return this.relativeX;
    }

    public double getRelativeY() {
        return this.relativeY;
    }
}
