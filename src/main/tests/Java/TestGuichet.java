package Java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class TestGuichet extends Java.TestEtape {

    @BeforeEach
    void setUp() {
        FabriqueNumero.getInstance().reset();
    }

    @Test
    void testNumeroSemaphoreUnique() {
        Guichet g1 = new Guichet("G1");
        Guichet g2 = new Guichet("G2");
        Guichet g3 = new Guichet("G3");

        assertEquals(1, g1.getNumeroSemaphore());
        assertEquals(2, g2.getNumeroSemaphore());
        assertEquals(3, g3.getNumeroSemaphore());
    }

    @Test
    void testGuichetNumero() {
        Guichet g1 = new Guichet("G1");
        assertEquals(0, g1.getNumero());

        Activite act = new Activite("A");
        Activite act2 = new Activite("A2");

        Guichet g2 = new Guichet("G2", 2);
        assertEquals(2,g2.getNumeroSemaphore());
        assertEquals(3,g2.getNumero());
    }
}
