/**
 * @file main.c
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Fichier principal pour la simulation du parcours des clients dans un graphe
 * @version 0.1
 * @date 2025-02-16
 * 
 * @copyright Copyright (c) 2025
 * 
 */

#include "../ressources/codeC/def.h"
#include "client3.h"

#define TMP_ATTENTE 1

/**
 * @brief Structure contenant les informations relatives à la simulation
 * 
 */
typedef struct {
    int nb_client;
    int nb_guichet;
    int nb_etape;
} info_simu;

/**
 * @brief Fonction qui initialise les informations de la simulation
 * 
 * @return info_simu 
 */
info_simu initialisation() {
    info_simu info;
    info.nb_client = get_nb_clients();
    info.nb_guichet = get_nb_guichets();
    info.nb_etape = get_nb_etapes();
    return info;
}

/**
 * @brief Fonction qui affiche les informations relatives à la simulation
 * 
 * @param nb_client Le nombre de clients
 * @param nb_guichet Le nombre de guichets
 * @param nb_etape Le nombre d'étapes
 */
void afficher_info_simu(int nb_client, int nb_guichet, int nb_etape) {
    printf("nb client: %d\n",nb_client);
    printf("nb guichet: %d\n",nb_guichet);
    printf("nb etape: %d\n\n",nb_etape);
}

/**
 * @brief Fonction qui affiche le PID d'un tableau de clients
 * 
 * @param tabPid Le tableau de PID des clients
 * @param nb_Client Le nombre de clients
 */
void afficher_pid_client(int* tabPid, int nb_client) {

    printf("Les clients: ");
    for (int i = 0; i < nb_client-1; i++) {
        printf("%d,", tabPid[i]);
    }
    printf("%d\n",tabPid[nb_client-1]);
}

/**
 * @brief Fonction qui exécute une simulation de parcours de clients dans un graphe
 * 
 * @param nb_client Le nombre de clients de la simulation
 * @param nb_etape Le nombre d'étapes de la simulation
 */
void simule_clients(int nb_client, int nb_etape) {
    int*position = ou_sont_les_clients(nb_etape,nb_client);

    while (position[(nb_client + 1)] < nb_client) { //  Tant que tous les clients ne sont pas dans la dernière activité, nbact-1 car on commence à 0
        position = ou_sont_les_clients(nb_etape, nb_client);

        for (int i = 0; i < nb_etape; i++) {
            int nb_clients = position[i * (nb_client + 1)];
            printf("Étape %d : %d clients :", i, nb_clients);

            for (int j = 0; j < nb_clients; j++) {
                printf("%d ", position[i * (nb_client + 1) + 1 + j]); // ex activité 1, 6 = index de nb clients, PIDs 7, 8, 9, 10, 11 (6 + 1 + j)
            }
            printf("\n");
        }

        sleep(TMP_ATTENTE);
        printf("\n");
    }

    printf("Fin simulation\n");
}

int main(int argc, char **argv) {
    info_simu info = initialisation();

    afficher_info_simu(info.nb_client, info.nb_guichet, info.nb_etape);

    int *tabjetons = get_tabjetons();

    int *tabPid = start_simulation(info.nb_etape, info.nb_guichet, info.nb_client, tabjetons);

    afficher_pid_client(tabPid, info.nb_client);

    simule_clients(info.nb_client, info.nb_etape);

    nettoyage();
    return 0;
}