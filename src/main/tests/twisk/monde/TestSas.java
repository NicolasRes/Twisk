package twisk.monde;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSas {
    @Test
    void testToString() {
        SasEntree entree = new SasEntree();
        SasSortie sortie = new SasSortie();
        Guichet gui = new Guichet("Guigui", 2);
        Activite act = new Activite("Acti");
        entree.ajouterSuccesseur(gui);

        assertEquals("Entrée : 1 successeur - Guigui", entree.toString());
        assertEquals("Guigui", entree.getSuccesseur(0).getNom());
        assertEquals(1, entree.nbSuccesseur());

        entree.ajouterSuccesseur(act);
        assertEquals("Entrée : 2 successeur - Guigui - Acti", entree.toString());
        assertEquals("Sortie : 0 successeur", sortie.toString());
    }
}
