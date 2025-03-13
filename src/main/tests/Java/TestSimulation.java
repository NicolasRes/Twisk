package Java;

import org.junit.jupiter.api.Test;
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
    void testSimuler() {
        Monde monde = FabriqueMonde.fabriqueMondeBasique();

        Simulation simu = new Simulation();

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
}