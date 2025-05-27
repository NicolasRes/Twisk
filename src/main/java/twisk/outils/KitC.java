package twisk.outils;

import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

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
    public void creerEnvironnement() {
        Path directory = Paths.get("/tmp/twisk");

        try {
            Files.createDirectories(directory);

            String projectRoot = System.getProperty("user.dir");
            Path resourcePath = Paths.get(projectRoot, "src", "main", "ressources", "codeC");

            String[] liste = {"programmeC.o", "def.h", "programmeCMac.o", "client.h", "codeNatif.o"};

            for (String nom : liste) {
                Path sourcePath = resourcePath.resolve(nom);
                if (!Files.exists(sourcePath)) {
                    System.err.println("ERREUR: Fichier non trouvé: " + sourcePath);
                    continue;
                }
                Path dest = directory.resolve(nom);
                Files.copy(sourcePath, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Erreur d'E/S lors de la création de l'environnement:");
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
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui construit dynamiquement la bibliothèque
     */
    public void construireLabBibliotheque(String nomBibliotheque) {
        if (System.getProperty("os.name").contains("Linux")) {
            ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "/tmp/twisk/programmeC.o", "/tmp/twisk/codeNatif.o", "/tmp/twisk/client.o", "-o", "/tmp/twisk/" + nomBibliotheque + ".so");
            try {
                pb.inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else if (System.getProperty("os.name").contains("Mac")) {
            ProcessBuilder pb = new ProcessBuilder("gcc", "-dynamiclib", "/tmp/twisk/programmeCMac.o", "/tmp/twisk/client.o", "-o", "/tmp/twisk/" + nomBibliotheque + ".dylib");
            try {
                pb.inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void tuerProcessus(GestionnaireClients gestionnaireClients) throws IOException{
        if(gestionnaireClients != null) {
            for (Client c : gestionnaireClients) {
                Runtime.getRuntime().exec("kill -9 " + c.getNumeroClient());
            }
        }
    }
}