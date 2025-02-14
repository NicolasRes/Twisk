CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

libTwisk.so: $(OBJECTS)
	gcc -shared ../ressources/codeC/programmeCLinux.o client.o -o libTwisk.so

client.o: client.c
	gcc -Wall -fPIC -c client.c

main : main.c
	gcc -Wall -L. main.c client.c -o main -lTwisk
	export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.

clear :
	rm -rf *.o
	rm -f main