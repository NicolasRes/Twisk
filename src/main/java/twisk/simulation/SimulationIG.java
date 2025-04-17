package twisk.simulation;

import twisk.monde.Monde;
import twisk.mondeIG.MondeIG;

public class SimulationIG {

    private MondeIG mondeig;
    /*
    public void simuler() throws MondeException {

        //verifierMondeIG();

        // Monde monde = creerMonde();

        // monde.simuler();

    }
    */

    /**
     * Constructeur de la classe SimulationIG
     * @param monde Le monde à simuler
     */
    public SimulationIG(MondeIG monde) {
        this.mondeig = monde;
    }

    /**
     * Méthode qui vérifie que le monde est correct
     * @throws MondeException
     */
    /*
    public void verifierMondeIG() throws MondeException {

    }
    */
    /**
     * Méthode qui crée un monde
     * @return Le monde créé
     */

    public Monde creerMonde() {

        Monde monde = new Monde();

        return monde;
    }


}
