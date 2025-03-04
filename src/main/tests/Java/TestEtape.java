package Java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TestEtape {
    private Etape e;
    private Etape gui;
    private Etape act1;
    private Etape gui2;
    private Etape act2;
    private SasEntree sasEntree;
    private SasSortie sasSortie;

    @BeforeEach
    void setUp() {
        e = new Guichet("A",2);
        gui = new Guichet("B",2);
        act1 = new Activite("C",5,2);
        gui2 = new Guichet("D",2);
        act2 = new Activite("E",6,2);
        sasEntree = new SasEntree();
        sasSortie = new SasSortie();

    }

    @Test
    void ajouterSuccesseur() {
        e.ajouterSuccesseur(gui, act1);
        gui.ajouterSuccesseur(gui2);

        assertEquals(2, e.nbSuccesseur());
        assertEquals(gui, e.getSuccesseur(0));
        assertEquals(act1, e.getSuccesseur(1));
        assertEquals(1, gui.nbSuccesseur());
    }

    @Test
    void estUneActivite() {
        assertFalse(gui.estUneActivite());
        assertTrue(act1.estUneActivite());
    }

    @Test
    void estUnGuichet() {
        assertTrue(gui.estUnGuichet());
        assertFalse(act1.estUnGuichet());
    }

    @Test
    void iterator() {
        e.ajouterSuccesseur(gui, act1);

        Iterator<Etape> i = e.iterator();

        // Premier élément suivant
        assertTrue(i.hasNext(), "L'itérateur doit avoir un élément suivant");
        assertEquals(i.next(), gui);

        // Deuxième élément suivant
        assertTrue(i.hasNext(), "L'itérateur doit avoir un deuxième élément suivant");
        assertEquals(i.next(), act1);

        assertFalse(i.hasNext(), "L'itérateur ne doit pas avoir d'élément suivant");
    }

    @Test
    void testToString() {
        e.ajouterSuccesseur(gui, act1);
        gui.ajouterSuccesseur(act1);

        assertEquals("A : 2 successeur -> B, C", e.toString());
        assertEquals("B : 1 successeur -> C", gui.toString());
        assertEquals("C : 0 successeur -> ", act1.toString());
    }

    @Test
    void iteratorSansSuccesseur() {
        Iterator<Etape> i = e.iterator();

        assertFalse(i.hasNext(), "L'itérateur ne doit pas avoir d'élément suivant pour une étape sans successeurs");
    }

    @Test
    void testToC() {

        sasEntree.ajouterSuccesseur(act1);
        act1.ajouterSuccesseur(act2);
        act2.ajouterSuccesseur(sasSortie);

        System.out.println(sasEntree.toC());


    }
}