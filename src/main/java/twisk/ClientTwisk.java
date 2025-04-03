package twisk;

import twisk.monde.*;
import twisk.outil.ClassLoaderPerso;
import twisk.outil.FabriqueMonde;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {
    public Object instance;
    public Method setNbClient;
    public Method simuler;
    public ClassLoaderPerso clp;
    public int nbClients;

    public void simulation(Monde monde) {
        this.nbClients = 6;
        try {
            clp = new ClassLoaderPerso(getClass().getClassLoader());

            Class<?> simulationClass = clp.loadClass("twisk.simulation.Simulation");

            Constructor<?> constructeur = simulationClass.getConstructor();
            instance = constructeur.newInstance();
            System.out.println("Constructeur");

            setNbClient = simulationClass.getMethod("setNbClients", int.class);
            setNbClient.invoke(instance, nbClients);

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
