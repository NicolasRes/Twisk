package twisk.monde;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMonde {

    @Test
    void aCommeEntree() {
        Monde monde = new Monde();
        Etape e1 = new Activite("e1");
        Etape e2 = new Activite("e2");

        e1.ajouterSuccesseur(e2);
        monde.aCommeEntree(e1);

        assertEquals(1, monde.getEntree(0).nbSuccesseur());
        assertEquals(e1, monde.getEntree(0));       // e1 est une entrée
        assertNotEquals(e2, monde.getEntree(0));    // e2 n'en est pas une
    }

    @Test
    void aCommeSortie() {
    }

    @Test
    void ajouterEtNbEtapes() {
        Monde monde = new Monde();  // Entrée et sortie comptent
        Etape e1 = new Activite("e1");
        Etape e2 = new Guichet("e2");

        assertEquals(2, monde.nbEtapes());

        monde.ajouter(e1);
        assertEquals(3, monde.nbEtapes());

        monde.ajouter(e2);
        assertEquals(4, monde.nbEtapes());
    }

    @Test
    void nbGuichets() {
        Monde monde = new Monde();
        Etape e1 = new Activite("e1");
        Etape e2 = new Guichet("e2");

        assertEquals(0, monde.nbGuichets());

        monde.ajouter(e1);  // e1 ne comptera pas comme un guichet
        monde.ajouter(e2);

        assertEquals(1, monde.nbGuichets());
    }

    @Test
    void testToString() {
        Monde monde = new Monde();
        Etape e1 = new Activite("e1");
        Etape e2 = new Guichet("e2");
        Etape e3 = new Activite("e3");
        Etape e4 = new Activite("e4");
        Etape e5 = new Activite("e5");

        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e5);
        e4.ajouterSuccesseur(e5);

        monde.ajouter(e2, e3, e4);

        monde.aCommeEntree(e1);
        monde.aCommeSortie(e5);

        String texteAttendu = "Entrée : 1 successeur(s) - e1\n" +
                "Sortie : 0 successeur(s)\n" +
                "e2 : 1 successeur -> e3\n" +
                "e3 : 1 successeur -> e5\n" +
                "e4 : 1 successeur -> e5\n";

        assertEquals(texteAttendu, monde.toString());
    }

    @Test
    void testToC() {

        Monde monde = new Monde();
        Etape a1 = new Activite("e1",5,2);
        Guichet g1 = new Guichet("g1",2);
        Etape a2 = new Activite("e2",5,2);

        a1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(a2);
        monde.ajouter(a1,g1,a2);

        monde.aCommeEntree(a1);
        monde.aCommeSortie(a2);

        System.out.println(monde.toC());


    }
}