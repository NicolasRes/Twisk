package twisk.mondeIG;

import twisk.monde.Etape;

import java.util.HashMap;

public class CorrespondancesEtapes {
    private HashMap<Integer, EtapeIG> etapesIG;
    private HashMap<Integer, Etape> etapes;

    public CorrespondancesEtapes() {
        this.etapesIG = new HashMap<>();
        this.etapes = new HashMap<>();
    }

    public void ajouter(EtapeIG etapeIG, Etape etape) {
        int identifiant = etapeIG.getIdentifiantEtape();
        this.etapesIG.put(identifiant, etapeIG);
        this.etapes.put(identifiant, etape);
    }

    public Etape get(EtapeIG etapeIG) {
        return etapes.get(etapeIG.getIdentifiantEtape());
    }

    public void afficherHashmap(CorrespondancesEtapes c) {
        for(Integer e : c.getHashmapIG().keySet()) {
            System.out.println("IG " + e + "->" + this.getHashmapIG().get(e));
        }
        for(Integer e : c.getHashmap().keySet()) {
            System.out.println("Monde " + e + "->" + this.getHashmap().get(e));
        }
        System.out.println();
    }

    public HashMap<Integer, EtapeIG> getHashmapIG() {
        return this.etapesIG;
    }

    public HashMap<Integer, Etape> getHashmap() {
        return this.etapes;
    }

    public void afficherCorrespondances() {
        for (Integer id : etapesIG.keySet()) {
            System.out.println("Identifiant: " + id);
            System.out.println("   EtapeIG: " + etapesIG.get(id));
            System.out.println("   Etape  : " + etapes.get(id));
        }
    }
}
