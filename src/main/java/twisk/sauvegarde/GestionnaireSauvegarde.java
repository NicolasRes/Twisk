package twisk.sauvegarde;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import twisk.mondeIG.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

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
        MondeDTO dto = construireMondeDTO(monde);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dto);

        // On écrit le json dans une fichier qui se trouve au chemin choisi lors de l'appel de sauvegarderMonde
        FileWriter writer = new FileWriter(cheminFichier);
        writer.write(json);
        writer.close();
    }

    /**
     * Méthode qui permet de charger un MondeIG depuis des données
     * @param cheminFichier Le chemin vers le fichier à charger
     * @return Le MondeIG
     */
    public static MondeIG chargerMonde(String cheminFichier) throws IOException {
        String json = Files.readString(Path.of(cheminFichier));
        Gson gson = new Gson();
        MondeDTO dto = gson.fromJson(json, MondeDTO.class);

        return reconstruireMondeIG(dto);
    }

    /**
     * Méthode pour construire le monde DTO à partir des infos du MondeIG
     * @param monde Le MondeIG
     * @return Un monde DTO
     */
    private static MondeDTO construireMondeDTO(MondeIG monde) {
        ArrayList<EtapeDTO> etapes = new ArrayList<>();
        ArrayList<ArcDTO> arcs = new ArrayList<>();

        // On remplit tous les champs des étapes DTO avec les infos des EtapeIG
        for(EtapeIG etapeIG : monde) {
            EtapeDTO eDTO = new EtapeDTO(
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

            etapes.add(eDTO);    // On ajoute la version DTO de l'étape dans la liste d'étapes à sauvegarder
        }

        // On fait la même chose avec les arcs en remplissant les champs des ArcIGDTO
        for(ArcIG arc : monde.getArcs()) {
            String idSrc = arc.getSource().getIdentifiant();
            String idDest = arc.getDestination().getIdentifiant();
            ArcDTO arcDTO = new ArcDTO(idSrc, idDest);
            arcs.add(arcDTO);
        }

        return new MondeDTO(etapes, arcs);
    }

    /**
     * Méthode qui permet d'importer / de reconstruire le MondeIG à partir des données d'un monde (JSON)
     * @param dto Les données du monde à reconstruire
     * @return Le MondeIG
     */
    private static MondeIG reconstruireMondeIG(MondeDTO dto) {
        MondeIG monde = new MondeIG();

        // On vide l'étape créée par défaut dans MondeIG
        monde.getEtapes().clear();

        HashMap<String, PointDeControleIG> pdcIdentifiants = new HashMap<>();

        // Reconstruction des étapes
        for(EtapeDTO eDTO : dto.getEtapes()) {
            EtapeIG etape = reconstruireEtapeIG(eDTO);
            etape.positionnerPDC();

            if(eDTO.estEntree()) etape.switchEntree();
            if(eDTO.estSortie()) etape.switchSortie();

            monde.getEtapes().put(etape.getIdentifiant(), etape);

            for (PointDeControleIG pdc : etape) {
                pdcIdentifiants.put(pdc.getIdentifiant(), pdc);
            }
        }

        // Reconstruction des arcs
        for(ArcDTO arcDTO : dto.getArcs()) {
            PointDeControleIG src = pdcIdentifiants.get(arcDTO.getIdSource());
            PointDeControleIG dst = pdcIdentifiants.get(arcDTO.getIdDestination());

            if(src != null && dst != null) {
                monde.getArcs().add(new ArcIG(src, dst));
            }
        }

        return monde;
    }

    /**
     * Méthode qui permet de construire une EtapeIG à partir de données (JSON)
     * @param eDTO Les données de l'étape à reconstruire
     * @return L'EtapeIG reconstruite
     */
    private static EtapeIG reconstruireEtapeIG(EtapeDTO eDTO) {
        EtapeIG etape;

        if(eDTO.getType().equals("Guichet")) {
            GuichetIG g = new GuichetIG(eDTO.getNom(), eDTO.getLargeur(), eDTO.getHauteur());
            g.setNbJetons(eDTO.getNbJetons());
            etape = g;
        }
        else {
            ActiviteIG a = new ActiviteIG(eDTO.getNom(), eDTO.getLargeur(), eDTO.getHauteur());
            a.setDelai(eDTO.getDelai());
            a.setEcart(eDTO.getEcart());
            etape = a;
        }

        etape.setIdentifiant(eDTO.getIdentifiant());
        etape.setPosX(eDTO.getPosX());
        etape.setPosY(eDTO.getPosY());
        return etape;
    }
}