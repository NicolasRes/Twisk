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

#include "../ressources/codeC/def.h"

void simulation(int ids) {
    entrer(ACT1);
    transfert(ACT1, ACT2);
    delai(2, 1);   // 0 < delta < temps < 100
}