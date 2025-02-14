
libTwisk.so: $(OBJECTS)
	gcc -shared ../ressources/codeC/programmeCLinux.o client.o -o libTwisk.so

client.o: client.c
	gcc -Wall -fPIC client.c -c client.c