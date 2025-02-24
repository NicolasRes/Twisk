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
 #define NUM_GUICHET1 1

 static int tabjetons[NB_GUICHET] = {2};//nb jetons pour chaque guichet

 void simulation(int ids) {
     entrer(0);
     delai(5,1);
     transfert(0, 1);
        P(ids,NUM_GUICHET1);
        transfert(1, 2);
        delai(6,1);
     V(ids,NUM_GUICHET1);
     transfert(2, 3);

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