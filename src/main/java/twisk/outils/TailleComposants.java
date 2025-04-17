package twisk.outils;

/**
 * Classe responsable de la taille des différents composants
 */
public class TailleComposants {
    public static final int LARGEUR_ETAPE = 150;
    public static final int HAUTEUR_ETAPE = 100;
    public static final int LARGEUR_FENETRE = 800;
    public static final int HAUTEUR_FENETRE = 800;
    public static final int MARGE_BAS = 70;
    public static final int HAUTEUR_MINI_ACT = 50;  // Nécessaire car notre hbox est vide

    // Variables utilisées pour compenser le spacing et le padding attribués à la vbox et ainsi réajuster les points de contrôle
    public static final int PADDING_X = 10;
    public static final int PADDING_Y = 5;
    public static final int PADDING_SPACING = 15;
    public static final int MARGE_X_SUP = 28;
    public static final int RAYON_PDC = 5;

    // Arcs
    public static final int DECAL_ARC = 2;
    public static final int LONG_FLECHE = 10;
    public static final double ANGLE_FLECHE = Math.PI / 6;

    public static final int HAUTEUR_MENU = 32;
}