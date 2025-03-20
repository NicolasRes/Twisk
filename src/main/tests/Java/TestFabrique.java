package Java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.outil.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFabrique {

    @BeforeEach
    void setUp(){FabriqueNumero.getInstance().reset();}

    @Test
    void testNumeroUnique() {
        FabriqueNumero fab = FabriqueNumero.getInstance();
        int num1 = fab.getNumeroEtape();
        int num2 = fab.getNumeroEtape();

        assertEquals(num1 + 1, num2);
    }

    @Test
    void testReset() {
        FabriqueNumero fab = FabriqueNumero.getInstance();
        fab.getNumeroEtape();
        fab.getNumeroEtape();

        fab.reset();
        assertEquals(0, fab.getNumeroEtape());
    }

    @Test
    void testActivitéNumero() {
        Activite act = new Activite("A");
        assertEquals(0, act.getNumero());
    }
}
