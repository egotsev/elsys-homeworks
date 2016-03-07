CC=g++
CFLAGS=-Wall -std=c++11 -c
CSOURCES=maze.cpp figures.cpp

all: maze.o figures.o
	${CC} maze.o figures.o

figures.o: figures.cpp
	${CC} ${CFLAGS} figures.cpp
maze.o: maze.cpp
	${CC} ${CFLAGS} maze.cpp

clean:
	rm *.o

