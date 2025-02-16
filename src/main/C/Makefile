CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

# Règles spécifiques à Linux
linux: libTwisk.so

libTwisk.so: $(OBJECTS)
	gcc -Wall -shared -fPIC ../ressources/codeC/programmeCLinux.o client.o -o libTwisk.so

# Règles spécifiques à macOS
mac: libTwisk.dylib

libTwisk.dylib: $(OBJECTS)
	$(CC) $(LDFLAGS) -Wall ../ressources/codeC/programmeCMac.o client.o -o libTwisk.dylib

client.o: client.c
	gcc $(CFLAGS) -c client.c

main : main.c
	gcc -Wall -L. -Wl,-rpath,. main.c client.c -o main -lTwisk

run: main
	LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH ./main

clean:
	rm -rf *.o libTwisk.so main
