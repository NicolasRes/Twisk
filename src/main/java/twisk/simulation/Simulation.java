package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Monde;

public class Simulation {

    private Monde monde;
    public Simulation() {

    }

    public void simuler(Monde monde){

        for(Etape e : monde){
            e.toString();
        }
    }
}
