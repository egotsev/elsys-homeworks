CC=g++
CFLAGS=-c -Wall
LDFLAGS=
SOURCES=main.cpp cell.cpp cellfactory.cpp labyrinth.cpp svgcell.cpp svglabyrinth.cpp svgdrawer.cpp
OBJECTS=$(SOURCES:.cpp=.o)
EXECUTABLE=Generator

all: $(SOURCES) $(EXECUTABLE)
	
$(EXECUTABLE): $(OBJECTS) 
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

.cpp.o:
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm *.o && rm $(EXECUTABLE).exe && rm result.svg