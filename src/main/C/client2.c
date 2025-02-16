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
 #include "client2.h"
 
 #define NB_ETAPES2 5
 #define NB_CLIENTS2 4
 #define NB_GUICHET2 0

 void simulation(int ids) {
     entrer(0);
     delai(5, 1);   // 0 < delta < temps < 100
     transfert(0, 1);
     delai(6, 2);
     transfert(1, 2);
     delai(2, 1);
     transfert(2, 3);
 }
 
 int get_nb_etapes2(){
     return NB_ETAPES2;
 }
 
 int get_nb_guichets2(){
     return NB_GUICHET2;
 }
 
 int get_nb_clients2(){
     return NB_CLIENTS2;
 }