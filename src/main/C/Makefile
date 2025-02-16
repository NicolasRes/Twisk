CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

# Règles spécifiques à Linux
linux: libTwisk.so

libTwisk.so: $(OBJECTS)
	gcc -Wall -shared -fPIC ../ressources/codeC/programmeCLinux.o $(OBJECTS) -o libTwisk.so

# Règles spécifiques à macOS
mac: libTwisk.dylib

libTwisk.dylib: $(OBJECTS)
	$(CC) $(LDFLAGS) -Wall ../ressources/codeC/programmeCMac.o $(OBJECTS) -o libTwisk.dylib

client.o: client.c
	gcc $(CFLAGS) -c client.c

client2.o: client2.c
	gcc $(FLAGS) -c client2.c

main : main.c
	gcc -Wall -L. -Wl,-rpath,. main.c client2.c -o main -lTwisk

run: main
	LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH ./main

clear:
	rm -rf *.o libTwisk.so main
