package twisk.monde;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
        Etape gui = new Guichet("A");
        Etape act = new Activite("B");
        Etape act2 = new Activite("C");
        gui.ajouterSuccesseur(act, act2);

        Iterator<Etape> i = gui.iterator();

        // Premier élément suivant
        assertTrue(i.hasNext(), "L'itérateur doit avoir un élément suivant");
        assertEquals(i.next(), act);

        // Deuxième élément suivant
        assertTrue(i.hasNext(), "L'itérateur doit avoir un deuxième élément suivant");
        assertEquals(i.next(), act2);

        assertFalse(i.hasNext(), "L'itérateur ne doit pas avoir d'élément suivant");
    }
}