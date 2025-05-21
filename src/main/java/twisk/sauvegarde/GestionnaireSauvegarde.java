package twisk.sauvegarde;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import twisk.mondeIG.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe GestionnaireSauvegarde qui permet la gestion de la sauvegarde du MondeIG en JSON
 */
public class GestionnaireSauvegarde {

    /**
     * Méthode qui permet de sauvegarder un MondeIG en JSON dans un fichier
     * @param monde Le monde à sauvegarder
     * @param cheminFichier Le chemin du fichier sous forme de String
     */
    public static void sauvegarderMonde(MondeIG monde, String cheminFichier) throws IOException {
        MondeIGDTO dto = construireMondeDTO(monde);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dto);

        // On écrit le json dans une fichier qui se trouve au chemin choisi lors de l'appel de sauvegarderMonde
        FileWriter writer = new FileWriter(cheminFichier);
        writer.write(json);
        writer.close();
    }

    /**
     * Méthode pour construire le monde DTO à partir des infos du MondeIG
     * @param monde Le MondeIG
     * @return Un monde DTO
     */
    private static MondeIGDTO construireMondeDTO(MondeIG monde) {
        ArrayList<EtapeIGDTO> etapes = new ArrayList<>();
        ArrayList<ArcIGDTO> arcs = new ArrayList<>();
        ArrayList<String> entrees = new ArrayList<>();
        ArrayList<String> sorties = new ArrayList<>();

        // On remplit tous les champs des étapes DTO avec les infos des EtapeIG
        for(EtapeIG etapeIG : monde) {
            EtapeIGDTO eDTO = new EtapeIGDTO(
                etapeIG.getNom(),
                etapeIG.getIdentifiantEtape(),
                etapeIG.getPosX(),
                etapeIG.getPosY(),
                etapeIG.getLargeur(),
                etapeIG.getHauteur(),
                etapeIG.estEntree(),
                etapeIG.estSortie(),
                etapeIG.getType()
            );

            if(etapeIG.getType().equals("Activite")) {  // On n'oublie pas le délai et l'écart pour l'activité
                eDTO.setDelai(((ActiviteIG) etapeIG).getDelai());
                eDTO.setEcart(((ActiviteIG) etapeIG).getEcart());
            }
            else if(etapeIG.getType().equals("Guichet")) {  // et les jetons pour le guichet
                eDTO.setNbJetons(((GuichetIG) etapeIG).getNbJetons());
            }

            // On sauvegarde aussi les infos sur les entrées et sorties
            if(etapeIG.estEntree()) {
                entrees.add(String.valueOf(etapeIG.getIdentifiantEtape()));
            }
            if(etapeIG.estSortie()) {
                sorties.add(String.valueOf(etapeIG.getIdentifiantEtape()));
            }

            etapes.add(eDTO);    // On ajoute la version DTO de l'étape dans la liste d'étapes à sauvegarder
        }

        // On fait la même chose avec les arcs en remplissant les champs des ArcIGDTO
        for(ArcIG arc : monde.getArcs()) {
            String idSrc = arc.getSource().getIdentifiant();
            String idDest = arc.getDestination().getIdentifiant();
            ArcIGDTO arcDTO = new ArcIGDTO(idSrc, idDest);
            arcs.add(arcDTO);
        }

        return new MondeIGDTO(etapes, arcs, entrees, sorties);
    }
}