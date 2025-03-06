/**
 * @file client3.h
 * @author RESENDE Nicolas et BURNEL Mathias
 * @brief Bibliothèque de client3.c
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

  /**
  * @brief Fonction qui récupère le nombre de jetons des clients des guichets
  * 
  * @return int* Tableau contenant les jetons des clients pour les guichets
  */
 int* get_tabjetons();
 
 #endif