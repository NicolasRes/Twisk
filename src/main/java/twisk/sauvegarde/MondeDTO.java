package twisk.sauvegarde;

import java.util.ArrayList;

/**
 * Classe MondeIGDTO qui sert à conserver les données d'un MondeIG pour sa conversion en JSON et sauvegarde
 */
public class MondeDTO {
    public ArrayList<EtapeDTO> etapes;
    public ArrayList<ArcDTO> arcs;

    /**
     * Constructeur de la classe MondeIGDOT
     * @param etapes les étapes version dto
     * @param arcs les arcs version dto
     */
    public MondeDTO(ArrayList<EtapeDTO> etapes, ArrayList<ArcDTO> arcs) {
        this.etapes = etapes;
        this.arcs = arcs;
    }

    /**
     * Méthode qui récupère la liste d'étapes DTO
     * @return La liste d'étapes DTO
     */
    public ArrayList<EtapeDTO> getEtapes() {
        return this.etapes;
    }

    /**
     * Méthode qui récupère la liste d'arcs DTO
     * @return La liste d'arcs DTO
     */
    public ArrayList<ArcDTO> getArcs() {
        return this.arcs;
    }
}