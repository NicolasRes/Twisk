CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

libTwisk.so: $(OBJECTS)
	gcc -shared -fPIC ../ressources/codeC/programmeCLinux.o client.o -o libTwisk.so

client.o: client.c
	gcc $(CFLAGS) -c client.c

main : main.c
	gcc -Wall -L. -Wl,-rpath,. main.c client.c -o main -lTwisk

run: main
	LD_LIBRARY_PATH=.:$$LD_LIBRARY_PATH ./main

clean:
	rm -rf *.o libTwisk.so main
