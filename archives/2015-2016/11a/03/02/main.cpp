#include "svglabyrinth.h"
#include "cell.h"

#include <sstream>
#include <string>

#define DEFAULT_LABYRINTH_SIZE 50
#define DEFAULT_CELL_SIZE 10

int main(int argc, char *argv[])
{
	int labSize = DEFAULT_LABYRINTH_SIZE,
		cellSize = DEFAULT_CELL_SIZE;

	if(argc == 3)
	{
		std::stringstream convert;

		convert << " " << argv[1];
		convert << " " << argv[2];

		convert >> labSize;
		convert >> cellSize;
	}

	SVGLabyrinth lab(labSize, labSize, Point(cellSize, cellSize));
	lab.Generate(Point(0,0), Point(labSize-1, labSize-1));
	lab.Draw();
}