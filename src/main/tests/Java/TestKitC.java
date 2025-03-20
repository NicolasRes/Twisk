package Java;

import org.junit.jupiter.api.Test;
import twisk.outil.KitC;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestKitC {

    @Test
    void testCreerFichier() {
        KitC kitC = new KitC();

        String codeC = "#include <stdio.h>\n" +
                "\n" +
                "int main() {\n" +
                "    printf(\"Test création fichier C\\n\");\n" +
                "    return 0;\n" +
                "}";

        kitC.creerEnvironnement();
        kitC.creerFichier(codeC);
        Path cheminClientC = Paths.get("/tmp/twisk/client.c");

        assertTrue(Files.exists(cheminClientC));
    }
}