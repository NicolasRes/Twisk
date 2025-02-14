#include "../ressources/codeC/def.h"

int main(int argc, char** argv) {

    int *tabPid = start_simulation(1, 0, 3, NULL);

    for (int i = 0; i < 3; i++) {
        printf("pid du client %d : %d\n", i, tabPid[i]);
    }
    void nettoyage();
    return 0;
}