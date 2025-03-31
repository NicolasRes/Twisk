package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe qui gère les objets de type Client
 */
public class GestionnaireClients implements Iterable<Client> {
    private ArrayList<Client> clients;

    /**
     * Constructeur de la classe GestionnaireClients
     */
    public GestionnaireClients() {
        this.clients = new ArrayList<>();
    }

    /**
     * Méthode qui set des clients dans le gestionnaire de clients
     * @param tabClients Les clients à set dans le gestionnaire
     */
    public void setClients(int... tabClients) {
        for(int c : tabClients) {
            Client client = new Client(c);
            this.clients.add(client);
        }
    }

    /**
     * Méthode qui permet de déplacer des clients à travers les étapes
     * @param numeroClient Le numéro du client à déplacer
     * @param etape L'étape dans laquelle déplacer les clients
     * @param rang Le rang auquel déplacer les clients
     */
    public void allerA(int numeroClient, Etape etape, int rang) {
        for(Client c : this.clients) {
            if(c.getNumeroClient() == numeroClient) {
                c.allerA(etape, rang);
                break;
            }
        }
    }

    /**
     * Méthode qui fait le ménage dans les clients pour traiter une nouvelle simulation
     */
    public void nettoyer() {
        this.clients.clear();
    }

    /**
     * Méthode qui permet d'itérer sur un gestionnaire d'étapes
     * @return L'itérateur du gestionnaire d'étapes
     */
    public Iterator<Client> iterator() {
        return this.clients.iterator();
    }
}