package Java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.monde.SasSortie;
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

    @Test
    void testToC() {
        Guichet g1 = new Guichet("G1",2);
        Activite act = new Activite("A",5,2);
        g1.ajouterSuccesseur(act);
        System.out.println(g1.toC());

    }

    @Test
    void testGuichetToC() {
        Activite act1 = new Activite("A1");
        Guichet g1 = new Guichet("G1");
        Activite act2 = new Activite("A2");
        Activite act3 = new Activite("A3");
        SasSortie sas = new SasSortie();

        act1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(act2);
        act2.ajouterSuccesseur(act3);
        act3.ajouterSuccesseur(sas);

        String texteAttendu = "P(ids,1); \n" +
                "transfert(1, 2); \n" +
                "delai(0, 0); \n" +
                "V(ids,1); \n" +
                "transfert(2, 3); \n" +
                "delai(0, 0); \n" +
                "transfert(3, 4);\n";

        assertEquals(texteAttendu, g1.toC());

        System.out.println(g1.toC());
    }
}
