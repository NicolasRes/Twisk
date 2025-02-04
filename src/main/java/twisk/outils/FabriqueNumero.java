package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance;
    private int cptEtape;

    private FabriqueNumero() {
        cptEtape = 0;
    }

    public static FabriqueNumero getInstance() {
        if (instance == null) {
            instance = new FabriqueNumero();
        }
        return instance;
    }

    public int getNumeroEtape() {
        cptEtape++;
        return cptEtape -1 ;
    }

    public void reset() {
        cptEtape = 0;
    }
}
