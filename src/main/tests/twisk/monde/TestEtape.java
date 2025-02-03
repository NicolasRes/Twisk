package twisk.monde;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestEtape {

    @Test
    void ajouterSuccesseur() {
        Etape e = new Guichet("A");
        Etape gui = new Guichet("B");
        Etape act = new Activite("C");

        e.ajouterSuccesseur(gui, act);
        assertEquals(2, e.nbSuccesseur());
        assertEquals(gui, e.getSuccesseur(0));
        assertEquals(act, e.getSuccesseur(1));
    }

    @Test
    void estUneActivite() {
        Etape gui = new Guichet("A");
        Etape act = new Activite("B");
        assertFalse(gui.estUneActivite());
        assertTrue(act.estUneActivite());
    }

    @Test
    void estUnGuichet() {
        Etape gui = new Guichet("A");
        Etape act = new Activite("B");
        assertTrue(gui.estUnGuichet());
        assertFalse(act.estUnGuichet());
    }

    @Test
    void iterator() {
    }
}