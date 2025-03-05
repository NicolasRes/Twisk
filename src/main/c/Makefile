CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client4.c
OBJECTS=$(SOURCES:.c=.o)

# Règles spécifiques à Linux
linux: libTwisk.so

# Dépendance à `Makefile` pour recompiler si `SOURCES` change
libTwisk.so: $(OBJECTS) Makefile
	gcc -Wall -shared -fPIC ../ressources/codeC/programmeC.o $(OBJECTS) -o libTwisk.so

# Règles spécifiques à macOS
mac: libTwisk.dylib

libTwisk.dylib: $(OBJECTS)
	$(CC) $(LDFLAGS) -Wall ../ressources/codeC/programmeCMac.o $(OBJECTS) -o libTwisk.dylib

client.o: client.c
	gcc $(CFLAGS) -c client.c

client2.o: client2.c
	gcc $(CFLAGS) -c client2.c

client3.o: client3.c
	gcc $(CFLAGS) -c client3.c

client4.o: client4.c
	gcc $(CFLAGS) -c client4.c

main : main.c
	gcc -Wall -L. -Wl,-rpath,. main.c $(SOURCES) -o main -lTwisk

run: main
	LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH ./main

rebuild: clear libTwisk.so

rebuild mac: clear mac

clear:
	rm -rf *.o libTwisk.so main