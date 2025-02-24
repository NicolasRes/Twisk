/**
 * @file client2.c
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Fichier client2
 * @version 0.1
 * @date 2025-02-14
 * 
 * @copyright Copyright (c) 2025
 * 
 */
 
 #include "../ressources/codeC/def.h"
 #include "client3.h"
 
 #define NB_ETAPES 4
 #define NB_CLIENTS 6
 #define NB_GUICHET 1
 #define SEMAPHORE_GUICHET1 1

 #define SAAS_ENTREE 0
 #define GUICHET 3
 #define ACTIVITE2 2
 #define SAS_SORTIE 1

static int tabjetons[NB_GUICHET] = {2};//nb jetons pour chaque guichet

 void simulation(int ids) {
     entrer(SAAS_ENTREE);
     delai(5,1);
     transfert(SAAS_ENTREE, GUICHET);
        P(ids,SEMAPHORE_GUICHET1);
        transfert(GUICHET, ACTIVITE2); //ici pas plus de 2 personnes
        delai(6,1);
     V(ids,SEMAPHORE_GUICHET1);
     transfert(ACTIVITE2, SAS_SORTIE);
     delai(4,2);

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

 int* get_tabjetons(){
     return tabjetons;
 }