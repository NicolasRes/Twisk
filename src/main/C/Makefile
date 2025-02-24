CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

# Règles spécifiques à Linux
linux: libTwisk.so

# Dépendance à `Makefile` pour recompiler si `SOURCES` change
libTwisk.so: $(OBJECTS) Makefile
	gcc -Wall -shared -fPIC ../ressources/codeC/programmeCLinux.o $(OBJECTS) -o libTwisk.so

# Règles spécifiques à macOS
mac: libTwisk.dylib

libTwisk.dylib: $(OBJECTS)
	$(CC) $(LDFLAGS) -Wall ../ressources/codeC/programmeCMac.o client2.o -o libTwisk.dylib

client.o: client.c
	gcc $(CFLAGS) -c client.c

client2.o: client2.c
	gcc $(CFLAGS) -c client2.c

main : main.c
	./update_include.sh
	gcc -Wall -L. -Wl,-rpath,. main.c $(SOURCES) -o main -lTwisk

run: main
	LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH ./main

rebuild: clear libTwisk.so

clear:
	rm -rf *.o libTwisk.so main
