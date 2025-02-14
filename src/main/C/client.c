/**
 * @file client.c
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Fichier client
 * @version 0.1
 * @date 2025-02-14
 * 
 * @copyright Copyright (c) 2025
 * 
 */

#define NB_ETAPES 3
#define NB_CLIENTS 3
#define NB_GUICHET 0


#include "../ressources/codeC/def.h"
#include "client.h"
/**
*    @brief Fonction qui simule le client
*
*/
void simulation(int ids) {
    entrer(0);
    transfert(0, 1);
    delai(2, 1);   // 0 < delta < temps < 100
    transfert(1, 2);
}

int get_nb_etapes(){
    return NB_ETAPES;
}

int get_nb_guichets(){
    return NB_GUICHET;
}

int get_nb_clients(){
    return NB_CLIENTS;
}