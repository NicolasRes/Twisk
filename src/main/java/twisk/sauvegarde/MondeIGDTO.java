package twisk.sauvegarde;

import java.util.ArrayList;

/**
 * Classe MondeIGDTO qui sert à conserver les données d'un MondeIG pour sa conversion en JSON et sauvegarde
 */
public class MondeIGDTO {
    public ArrayList<EtapeIGDTO> etapes;
    public ArrayList<ArcIGDTO> arcs;
    public ArrayList<String> entrees;
    public ArrayList<String> sorties;

    /**
     * Constructeur de la classe MondeIGDOT
     * @param etapes les étapes version dto
     * @param arcs les arcs version dto
     * @param entrees les entrées version dto
     * @param sorties les sorties version dto
     */
    public MondeIGDTO(ArrayList<EtapeIGDTO> etapes, ArrayList<ArcIGDTO> arcs, ArrayList<String> entrees, ArrayList<String> sorties) {
        this.etapes = etapes;
        this.arcs = arcs;
        this.entrees = entrees;
        this.sorties = sorties;
    }
}