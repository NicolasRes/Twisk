/**
 * @file def.h
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Bibliothèque de programmeC
 * @version 0.1
 * @date 2025-02-16
 * 
 * @copyright Copyright (c) 2025
 * 
 */

#ifndef DEF_H
#define DEF_H

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// Constantes
#define ACT1 1
#define ACT2 2

// fonctions déclarées dans programmeC, à utiliser dans le code C du client ******************************************************

/**
 * @brief Fonction qui ajoute un délai variable entre deux étapes
 * 
 * @param temps Temps d'attente fixe
 * @param delta Temps d'attente variable
 */
void delai(int temps, int delta) ;

/**
 * @brief Fonction qui définit l'étape d'entrée d'un monde
 * 
 * @param etape L'étape d'entrée
 */
void entrer(int etape) ;

/**
 * @brief Fonction qui permet le transfert des clients d'une étape à une autre
 * 
 * @param source L'étape de départ
 * @param destination L'étape d'arrivée
 */
void transfert(int source, int destination) ;

/**
 * @brief Fonction qui demande la possibilité de prendre un jeton. Bloque le processus si aucun jeton. Débloqué automatiquement quand un autre processus rendra 1 jeton
 * 
 * @param semid Identifiant du numéro sémaphore à décrémenter
 * @param numero Numéro sémaphore à décrémenter
 * @return int Un booléen négatif si impossible de prendre un jeton (à vérifier)
 */
int P(int semid, int numero) ;

/**
 * @brief Fonction qui rend une ressource disponible à nouveau après que le processus a terminé de l'utiliser
 * 
 * @param semid Identifiant du numéro sémaphore à incrémenter
 * @param numero Le numéro sémaphore à incrémenter
 * @return int Un booléen négatif si le sémaphore n'existe pas (à vérifier)
 */
int V(int semid, int numero) ;

// fonctions déclarées dans programmeC, à utiliser dans le code java ************************************************************

/**
 * @brief Fonction qui permet de démarrer une simulation en appelant la fonction simulation du fichier client
 * 
 * @param nbEtapes Le nombre d'étapes de la simulation
 * @param nbGuichets Le nombre de guichets de la simulation
 * @param nbClients Le nombre de clients de la simulation
 * @param tabJetonsGuichets NULL
 * @return int* Un tableau avec les PID des clients
 */
int* start_simulation(int nbEtapes, int nbGuichets, int nbClients, int *tabJetonsGuichets) ;

/**
 * @brief Fonction qui détermine où se trouvent les clients
 * 
 * @param nbEtapes Le nombre d'étapes
 * @param nbClients Le nombre de clients
 * @return int* L'adresse du tableau où sont stockées les informations des clients
 */
int* ou_sont_les_clients(int nbEtapes, int nbClients) ;

/**
 * @brief Nettoie les structures de données créées
 * 
 */
void nettoyage() ;

// la fonction déclarée dans le code C des clients ******************************************************************************

/**
 * @brief Fonction qui simule le parcours d'un client dans le graphe
 * 
 * @param ids 
 */
void simulation(int ids) ;

#endif