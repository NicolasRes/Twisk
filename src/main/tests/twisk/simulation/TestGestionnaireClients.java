package twisk.simulation;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestGestionnaireClients {

    @Test
    void setClients() {
        int[] tabClients = {1, 2, 3, 4, 5};
        int[] numerosClients = new int[5];
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(tabClients);

        int index = 0;
        for(Client c : gestionnaireClients) {
            assertEquals(tabClients[index], c.getNumeroClient(), "Numéro client à l'indice " + index + " est incorrect");
            index++;
        }
    }

    @Test
    void allerA() {
        Etape act1 = new Activite("act1", 2, 1);
        Etape act2 = new Activite("act2", 3, 2);

        int[] tabClients = {1, 2};
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(tabClients);

        gestionnaireClients.allerA(1, act1, 1);
        gestionnaireClients.allerA(2, act2, 2);

        Client client1 = null;
        Client client2 = null;
        for(Client c : gestionnaireClients) {
            if(c.getNumeroClient() == 1) {
                client1 = c;
                break;
            }
        }

        for(Client c : gestionnaireClients) {
            if(c.getNumeroClient() == 2) {
                client2 = c;
                break;
            }
        }

        assertNotNull(client1.getEtape());
        assertEquals(act1, client1.getEtape());
        assertEquals(1, client1.getRang());

        assertNotNull(client2.getEtape());
        assertEquals(act2, client2.getEtape());
        assertEquals(2, client2.getRang());
    }
}