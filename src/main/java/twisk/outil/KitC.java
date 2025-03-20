package twisk.outil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class KitC {

    public KitC() {

    }

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
            String[] liste = {"programmeC.o", "def.h", "programmeCMac.o" , "client.h" , "codeNatif.o"};
            for (String nom : liste) {

                Path fichier = Paths.get("src/main/ressources/codeC/" + nom);
                Files.copy(fichier, directory.resolve(nom), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui crée le fichier client.c dans le répertoire /tmp/twisk
     * @param codeC Le code C à écrire dans le client.c
     */
    public void creerFichier(String codeC) {
        Path directory = Paths.get("/tmp/twisk/client.c");
        try {
            Files.writeString(directory, codeC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui compile un fichier C
     */
    public void compiler() {
        // Définir la commande à exécuter
        ProcessBuilder pb = new ProcessBuilder("gcc", "-Wall", "-c", "/tmp/twisk/client.c", "-o", "/tmp/twisk/client.o");
        try {
            /* Demander l'exécution de la compilation (start)
             inheritIO() permet de récupérer les affichages sur la sortie standard et la sortie d'erreur
             waitFor s'assure que la tâche demandée se termine avant de passer à la suite en demandant au processus d'attendre sa fin */
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui construit dynamiquement la bibliothèque
     */
    public void construireLabBibliotheque() {
        if (System.getProperty("os.name").contains("Linux")) {
            ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "/tmp/twisk/programmeC.o", "/tmp/twisk/codeNatif.o","/tmp/twisk/client.o", "-o", "/tmp/twisk/libTwisk.so");
            try {
                pb.inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
         else if (System.getProperty("os.name").contains("Mac")) {
            ProcessBuilder pb = new ProcessBuilder("gcc", "-dynamiclib", "/tmp/twisk/programmeCMac.o", "/tmp/twisk/client.o", "-o", "/tmp/twisk/libTwisk.dylib");
            try {
                pb.inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}