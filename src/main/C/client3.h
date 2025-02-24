/**
 * @file client.h
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Bibliothèque de client.2c
 * @version 0.1
 * @date 2025-02-16
 * 
 * @copyright Copyright (c) 2025
 * 
 */

 #ifndef CLIENT3_H
 #define CLIENT3_H
 
 /**
  * @brief Fonction qui récupère le nombre d'étapes du monde 2
  * 
  * @return int Le nombre d'étapes du monde
  */
 int get_nb_etapes();
 
 /**
  * @brief Fonction qui récupère le nombre de guichets du monde 2
  * 
  * @return int Le nombre de guichets du monde
  */
 int get_nb_guichets();
 
 /**
  * @brief Fonction qui récupère le nombre de clients du monde 2
  * 
  * @return int Le nombre de clients du monde
  */
 int get_nb_clients();

 int* get_tabjetons();
 
 #endif