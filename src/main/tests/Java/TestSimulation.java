package Java;

import org.junit.Test;
import twisk.monde.Monde;
import twisk.outils.FabriqueMonde;
import twisk.simulation.Simulation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimulation {

    @Test
    public void testSimuler() {
        Monde monde = FabriqueMonde.fabriqueMondeBifurc();

        Simulation simu = new Simulation();
        simu.setNbClients(6);

        System.out.println(monde.toC());

        simu.simuler(monde);

        Path cheminClientC = Paths.get("/tmp/twisk/client.c");
        assertTrue(cheminClientC.toFile().exists());

        String texteMondeC = monde.toC();

        try {
            String contenuFichier = Files.readString(cheminClientC);
            assertEquals(texteMondeC, contenuFichier);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (System.getProperty("os.name").contains("Linux")) {
            Path cheminLib = Paths.get("/tmp/twisk/libTwisk.so");
            assertTrue(cheminLib.toFile().exists());
        }
        else if (System.getProperty("os.name").contains("Mac")) {
            Path cheminLib = Paths.get("/tmp/twisk/libTwisk.dylib");
            assertTrue(cheminLib.toFile().exists());
        }
    }

    @Test
    public void testCarac() {
        String nom = "téèst";
        nom = nom.replaceAll("[^\\p{Alpha}]+", "_");
        System.out.println(nom);

        String nom2 = "téèst";
        nom2 = nom2.replace("é", "e");
        nom2 = nom2.replace("è", "e");
        System.out.println(nom2);

        String nom3 = "téèst";
        nom3 = nom3.replaceAll("é", "e, e");
        System.out.println(nom3);
    }
}