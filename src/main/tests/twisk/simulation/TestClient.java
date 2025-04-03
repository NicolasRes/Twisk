package twisk.simulation;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;

import static org.junit.jupiter.api.Assertions.*;

class TestClient {

    @Test
    void allerA() {
        Etape act = new Activite("act1", 2, 1);
        Client c = new Client(2);

        c.allerA(act, 1);

        assertEquals(act, c.getEtape());
        assertEquals(1, c.getRang());
        assertEquals(2, c.getNumeroClient());
    }
}