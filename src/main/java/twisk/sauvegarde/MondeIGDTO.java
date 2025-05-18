package twisk.sauvegarde;

import java.util.ArrayList;

/**
 * Classe MondeIGDTO qui sert à conserver les données d'un MondeIG pour sa conversion en JSON et sauvegarde
 */
public class MondeIGDTO {
    public ArrayList<EtapeIGDTO> etapes = new ArrayList<>();
    public ArrayList<ArcIGDTO> arcs = new ArrayList<>();
    public ArrayList<String> entrees = new ArrayList<>();
    public ArrayList<String> sorties = new ArrayList<>();
}
