package twisk.monde;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGestionnaireEtapes {

    @Test
    void ajouterEtNbEtape() {
        GestionnaireEtapes gest = new GestionnaireEtapes();
        Etape e1 = new Activite("e1");
        Activite e2 = new Activite("e2");
        Guichet e3 = new Guichet("e3");

        assertEquals(0, gest.nbEtapes());

        gest.ajouter(e1);
        assertEquals(1, gest.nbEtapes());

        gest.ajouter(e2, e3);
        assertEquals(3, gest.nbEtapes());
    }

    @Test
    void getEtape() {
        GestionnaireEtapes gest = new GestionnaireEtapes();
        Etape e1 = new Activite("e1");
        Etape e2 = new Activite("e2");

        gest.ajouter(e1);
        gest.ajouter(e2);

        assertEquals(e1, gest.getEtape(0));
        assertEquals(e2, gest.getEtape(1));
    }
}