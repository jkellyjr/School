# makefile for project 1 cs1550

LIB = library
MAIN = driver
GCC = gcc

install: 
	$(GCC) -o $(LIB).o -c $(LIB).c
	$(GCC) -o $(MAIN).o -c $(MAIN).c
	$(GCC) -o $(MAIN) $(MAIN).o $(LIB).o


clean:
	rm -f $(LIB).o
	rm -f $(LIB).o
	rm -f $(MAIN)

