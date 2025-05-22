package twisk.sauvegarde;

import java.util.ArrayList;

/**
 * Classe MondeIGDTO qui sert à conserver les données d'un MondeIG pour sa conversion en JSON et sauvegarde
 */
public class MondeIGDTO {
    public ArrayList<EtapeIGDTO> etapes;
    public ArrayList<ArcIGDTO> arcs;

    /**
     * Constructeur de la classe MondeIGDOT
     * @param etapes les étapes version dto
     * @param arcs les arcs version dto
     */
    public MondeIGDTO(ArrayList<EtapeIGDTO> etapes, ArrayList<ArcIGDTO> arcs) {
        this.etapes = etapes;
        this.arcs = arcs;
    }

    /**
     * Méthode qui récupère la liste d'étapes DTO
     * @return La liste d'étapes DTO
     */
    public ArrayList<EtapeIGDTO> getEtapes() {
        return this.etapes;
    }

    /**
     * Méthode qui récupère la liste d'arcs DTO
     * @return La liste d'arcs DTO
     */
    public ArrayList<ArcIGDTO> getArcs() {
        return this.arcs;
    }
}