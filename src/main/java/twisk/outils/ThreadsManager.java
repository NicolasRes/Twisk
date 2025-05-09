package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Classe qui gère les threads
 */
public class ThreadsManager {
    private static ThreadsManager instance;
    private ArrayList<Thread> lesThreads;

    /**
     * Constructeur de la classe ThreadsManager
     */
    public ThreadsManager() {
        this.lesThreads = new ArrayList<>();
    }

    /**
     * Méthode qui renvoie une instance de la classe ThreadsManager
     * @return L'unique instance de ThreadsManager
     */
    public static ThreadsManager getInstance() {
        if(instance == null) {
            instance = new ThreadsManager();
        }
        return instance;
    }

    /**
     * Méthode qui lance l'exécution d'une tâche dans un thread différent et l'ajoute à la liste des threads actifs
     * @param task La tâche JavaFX à exécuter en arrière-plan
     */
    public void lancer(Task task) {
        Thread thread = new Thread(task);
        this.lesThreads.add(thread);
        thread.start();
    }

    /**
     * Méthode qui interrompt et vide la liste de tous les threads en cours
     */
    public void detruireTout() {
        for(Thread thread : this.lesThreads) {
            thread.interrupt();
        }
        this.lesThreads.clear();
    }
}