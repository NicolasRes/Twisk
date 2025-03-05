package twisk.outils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class KitC {

    /**
     * Méthode qui crée des copies de fichiers dans un répertoire temporaire en vue d'intégrer du code C dans Java
     */
    public void creerEnvironnement(){
        Path directory = Paths.get("/tmp/twisk");
        try {
            // création du répertoire twisk sous /tmp.
            // Ne déclenche pas d’erreur si le répertoire existe déjà
             Files.createDirectories(directory);

            // copie des fichiers programmeC.o, def.h et programmeCMac.o sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h", "programmeCMac.o"};
            for (String nom : liste) {
                InputStream src = getClass().getResourceAsStream("/codeC/" + nom);
                Path dest = directory.resolve(nom);
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}