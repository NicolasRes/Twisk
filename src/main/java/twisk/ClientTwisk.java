package twisk;

import twisk.monde.*;
import twisk.outil.ClassLoaderPerso;
import twisk.outil.FabriqueMonde;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {
    public static Object instance;
    public static Method setNbClient;
    public static Method simuler;
    public static ClassLoaderPerso clp;
    public static int nbClients;

    public void simulation(Monde monde) {
        nbClients = 6;
        try {
            clp = new ClassLoaderPerso(ClientTwisk.class.getClassLoader());

            Class<?> simulationClass = clp.loadClass("twisk.simulation.Simulation");
            
            Constructor<?> constructeur = simulationClass.getConstructor();
            instance = constructeur.newInstance();

            setNbClient = simulationClass.getMethod("setNbClients", int.class);
            setNbClient.invoke(instance, nbClients);

            Method setNomBibliotheque = simulationClass.getMethod("setNomBibliotheque", String.class);
            setNomBibliotheque.invoke(instance, "libTwisk");

            simuler = simulationClass.getMethod("simuler", Monde.class);
            simuler.invoke(instance, monde);
        } catch (ClassNotFoundException e) {
            System.err.println("La classe Simulation n'a pas été trouvée");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("La méthode demandée n'existe pas");
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.err.println("Erreur lors de l'appel de la méthode");
            e.printStackTrace();
        }
    }

    public void main(String[] args) {
        Monde monde1 = FabriqueMonde.fabriqueMondeBasique();
        Monde monde2 = FabriqueMonde.fabriqueMondeBifurc();

        simulation(monde1);

        System.gc();

        simulation(monde2);
    }
}
