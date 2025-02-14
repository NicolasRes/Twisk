CC=gcc
CFLAGS=-Wall -fPIC
LDFLAGS=-shared
SOURCES=client.c
OBJECTS=$(SOURCES:.c=.o)

libTwisk.so: $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) -o libTwisk.so

client.o: client.c
	$(CC) $(CFLAGS) -c client.c